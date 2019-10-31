package com.fronke.betatransmit;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.fronke.betatransmit.adapters.EpisodeAdapter;
import com.fronke.betatransmit.services.BetaTransmitAlarmReceiver;
import com.fronke.betatransmit.services.BetaTransmitSchedulingService;
import com.fronke.betatransmit.utils.Preferences;
import com.kokotchy.betaSeriesAPI.api.jsonImpl.BetaSerieApi;
import com.kokotchy.betaSeriesAPI.model.Episode;
import com.kokotchy.betaSeriesAPI.model.SubtitleLanguage;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends Activity {

    private ListView mEpisodesListView;
    private Button mReloadBtn;

    private Preferences mUserPreferences;
    private EpisodeAdapter mAdapter;
    private ArrayList<Episode> mEpisodes = new ArrayList<>();
    BetaTransmitAlarmReceiver alarm = new BetaTransmitAlarmReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserPreferences = new Preferences(this.getApplicationContext());

        mEpisodesListView = (ListView) findViewById(R.id.main_episodes_listview);
        mReloadBtn = (Button) findViewById(R.id.main_reload_button);

        mAdapter = new EpisodeAdapter(this, mEpisodes);
        mEpisodesListView.setAdapter(mAdapter);

        mReloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, BetaTransmitSchedulingService.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        reload();
    }

    private void reload() {
        new LoadEpisodesTask(mUserPreferences.getBsUsername(),
                mUserPreferences.getBsPassword()).execute();
    }


    private void fetchSeries() {
        new LoadEpisodesTask(mUserPreferences.getBsUsername(),
                mUserPreferences.getBsPassword()).execute();
    }

    private class LoadEpisodesTask extends AsyncTask<Void, Integer, Boolean> {
        private final String mUsername;
        private final String mPassword;
        private Set<Episode> mLoadedEpisodes;

        public LoadEpisodesTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        protected Boolean doInBackground(Void... args) {
            String sessionToken = BetaSerieApi.getMembers().auth(mUsername, mPassword);
            mLoadedEpisodes = BetaSerieApi.getMembers().getEpisodes(sessionToken, SubtitleLanguage.ALL);
            return mLoadedEpisodes != null;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if (isSuccess) {
                mEpisodes.clear();
                for (Episode e : mLoadedEpisodes) {
                    mEpisodes.add(e);
                }
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_reload) {
            reload();
        }
        else if (id == R.id.action_start_alarm) {
            alarm.setAlarm(this);
        }
        else if (id == R.id.action_stop_alarm) {
            alarm.cancelAlarm(this);
        }
        return super.onOptionsItemSelected(item);
    }
}
