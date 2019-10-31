package com.fronke.betatransmit.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fronke.betatransmit.extratorrentApi.ExtratorrentApi;
import com.fronke.betatransmit.kickassApi.KickassApi;
import com.fronke.betatransmit.MainActivity;
import com.fronke.betatransmit.R;
import com.fronke.betatransmit.utils.Preferences;
import com.kokotchy.betaSeriesAPI.api.jsonImpl.BetaSerieApi;
import com.kokotchy.betaSeriesAPI.model.Episode;
import com.kokotchy.betaSeriesAPI.model.SubtitleLanguage;

import java.net.URI;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import nl.stil4m.transmission.api.TransmissionRpcClient;
import nl.stil4m.transmission.api.domain.AddTorrentInfo;
import nl.stil4m.transmission.api.domain.AddedTorrentInfo;
import nl.stil4m.transmission.rpc.RpcClient;
import nl.stil4m.transmission.rpc.RpcConfiguration;
import nl.stil4m.transmission.rpc.RpcException;


public class BetaTransmitSchedulingService extends IntentService {
    public BetaTransmitSchedulingService() {
        super("SchedulingService");
    }

    public int notificationId = 1;
    private NotificationManager mNotificationManager;

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

            if (isNetworkAvailable() && hour >= 10) {
                Preferences userPreferences = new Preferences(this.getApplicationContext());

                String[] katSearchParameters = userPreferences.getKatSearchParameter();
                String bsLogin = userPreferences.getBsUsername();
                String bsPassword = userPreferences.getBsPassword();
                String rpcEncodingAuth = userPreferences.getRpcPassword();
                String rpcHostServer = userPreferences.getRpcHostAddress();
                String rpcHostPort = userPreferences.getRpcHostPort();
                String rpcDefaultFolder = userPreferences.getRpcDefaultFolder();

                String sessionToken = BetaSerieApi.getMembers().auth(bsLogin, bsPassword);
                Set<Episode> episodes = BetaSerieApi.getMembers().getEpisodes(sessionToken, SubtitleLanguage.ALL);

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                RpcConfiguration rpcConfiguration = new RpcConfiguration();
                rpcConfiguration.setHost(URI.create("http://" + rpcHostServer + ":" + rpcHostPort + "/transmission/rpc"));
                rpcConfiguration.setAuthorization(rpcEncodingAuth);
                RpcClient client = new RpcClient(rpcConfiguration, objectMapper);
                TransmissionRpcClient rpcClient = new TransmissionRpcClient(client);

                for (Episode e : episodes) {
                    if (!e.isDownloaded()) {
                        String searchTerms = e.getShow() + " " + e.getNb() + " ";

                        List<String> torrentLinks = new ExtratorrentApi().getTorrentLinks(searchTerms, katSearchParameters);

                        int position = 0;
                        boolean isTorrentAdded = false;
                        while (!isTorrentAdded &&
                                torrentLinks != null &&
                                position < torrentLinks.size()) {

                            AddTorrentInfo addTorrentInfo = new AddTorrentInfo();
                            addTorrentInfo.setFilename(torrentLinks.get(position));
                            addTorrentInfo.setDownloadDir(rpcDefaultFolder);
                            addTorrentInfo.setPaused(false);
                            AddedTorrentInfo result = null;
                            try {
                                result = rpcClient.addTorrent(addTorrentInfo);
                            } catch (RpcException rpcEx) {
                                int test = 5;
                            }

                            if (result != null) {
                                isTorrentAdded = true;
                            }
                            position++;

                        }

                        if (isTorrentAdded) {
                            sendNotification("Episode downloaded", e.getShow() + " " + e.getNb(), true);
                            String episodeNb = e.getNb();
                            int season = Integer.parseInt(episodeNb.substring(1, episodeNb.indexOf('E')));
                            int episode = Integer.parseInt(episodeNb.substring(episodeNb.indexOf('E') + 1));
                            BetaSerieApi.getMembers().setDownloaded(sessionToken, e.getShowUrl(), season, episode);
                        } else {
                            sendNotification("Fail to download", e.getShow() + " " + e.getNb(), false);
                        }
                    }
                }
            }
        } catch (Exception exception) {
            sendNotification("Beta Transmit", "An error occured", false);
        }

        BetaTransmitAlarmReceiver.completeWakefulIntent(intent);
    }


    private void sendNotification(String title, String msg, boolean isPositive) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.notif_positive);
        if (!isPositive) {
            icon = BitmapFactory.decodeResource(getResources(), R.drawable.notif_negative);
        }

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notif_icon)
                        .setLargeIcon(icon)
                        .setContentTitle(title)
                        .setAutoCancel(true)
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(notificationId, mBuilder.build());
        notificationId++;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
