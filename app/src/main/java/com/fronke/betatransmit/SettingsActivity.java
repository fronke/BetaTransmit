package com.fronke.betatransmit;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fronke.betatransmit.utils.Preferences;

public class SettingsActivity extends Activity {

    private static final String DEFAULT_PASS = "xxxxxxxxxx";

    private EditText mKatSearchParameter1;
    private EditText mKatSearchParameter2;
    private EditText mKatSearchParameter3;
    private EditText mBsUsername;
    private EditText mBsPassword;
    private EditText mRpcUsername;
    private EditText mRpcPassword;
    private EditText mRpcHostAddress;
    private EditText mRpcHostPort;
    private EditText mRpcDefaultFolder;
    private Button mSaveBtn;

    private Preferences mUserPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mUserPreferences = new Preferences(this.getApplicationContext());

        mKatSearchParameter1 = (EditText) findViewById(R.id.preference_kat_search_parameter1);
        mKatSearchParameter2 = (EditText) findViewById(R.id.preference_kat_search_parameter2);
        mKatSearchParameter3 = (EditText) findViewById(R.id.preference_kat_search_parameter3);
        mBsUsername = (EditText) findViewById(R.id.preference_bs_username);
        mBsPassword = (EditText) findViewById(R.id.preference_bs_password);
        mRpcUsername = (EditText) findViewById(R.id.preference_rpc_username);
        mRpcPassword = (EditText) findViewById(R.id.preference_rpc_password);
        mRpcHostAddress = (EditText) findViewById(R.id.preference_rpc_host_address);
        mRpcHostPort = (EditText) findViewById(R.id.preference_rpc_host_port);
        mRpcDefaultFolder = (EditText) findViewById(R.id.preference_rpc_default_folder);
        mSaveBtn = (Button) findViewById(R.id.preference_save_btn);

        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferences();
            }
        });

        loadPreferences();
    }


    private void savePreferences() {
        String katSearchParameter1 = mKatSearchParameter1.getText().toString();
        String katSearchParameter2 = mKatSearchParameter2.getText().toString();
        String katSearchParameter3 = mKatSearchParameter3.getText().toString();
        String bsUsername = mBsUsername.getText().toString();
        String bsPassword = mBsPassword.getText().toString();
        String rpcUsername = mRpcUsername.getText().toString();
        String rpcPassword = mRpcPassword.getText().toString();
        String rpcHostAddress = mRpcHostAddress.getText().toString();
        String rpcHostPort = mRpcHostPort.getText().toString();
        String rpcDefaultFolder = mRpcDefaultFolder.getText().toString();

        String rpcAuth = Base64.encodeToString((rpcUsername + ":" + rpcPassword).getBytes(), Base64.NO_WRAP);

        if (!TextUtils.isEmpty(katSearchParameter1)) {
            mUserPreferences.setKatSearchParameter(katSearchParameter1, 1);
        }
        if (!TextUtils.isEmpty(katSearchParameter2)) {
            mUserPreferences.setKatSearchParameter(katSearchParameter2, 2);
        }
        if (!TextUtils.isEmpty(katSearchParameter3)) {
            mUserPreferences.setKatSearchParameter(katSearchParameter3, 3);
        }
        if (!TextUtils.isEmpty(bsUsername)) {
            mUserPreferences.setBsUsername(bsUsername);
        }
        if (!TextUtils.isEmpty(bsPassword) && !bsPassword.equals(DEFAULT_PASS)) {
            mUserPreferences.setBsPassword(bsPassword);
        }
        if (!TextUtils.isEmpty(rpcUsername)) {
            mUserPreferences.setRpcUsername(rpcUsername);
        }
        if (!TextUtils.isEmpty(rpcPassword) && !rpcPassword.equals(DEFAULT_PASS)) {
            mUserPreferences.setRpcPassword(rpcAuth);
        }
        if (!TextUtils.isEmpty(rpcHostAddress)) {
            mUserPreferences.setRpcHostAddress(rpcHostAddress);
        }
        if (!TextUtils.isEmpty(rpcHostPort)) {
            mUserPreferences.setRpcHostPort(rpcHostPort);
        }
        if (!TextUtils.isEmpty(rpcDefaultFolder)) {
            mUserPreferences.setRpcDefaultFolder(rpcDefaultFolder);
        }

        Toast.makeText(SettingsActivity.this, "Setting saved!", Toast.LENGTH_SHORT).show();
    }

    private void loadPreferences() {
        mKatSearchParameter1.setText(mUserPreferences.getKatSearchParameter()[0]);
        mKatSearchParameter2.setText(mUserPreferences.getKatSearchParameter()[1]);
        mKatSearchParameter3.setText(mUserPreferences.getKatSearchParameter()[2]);
        mBsUsername.setText(mUserPreferences.getBsUsername());
        if (!TextUtils.isEmpty(mUserPreferences.getBsPassword())) {
            mBsPassword.setText(DEFAULT_PASS);
        }
        mRpcUsername.setText(mUserPreferences.getRpcUsername());
        if (!TextUtils.isEmpty(mUserPreferences.getRpcPassword())) {
            mRpcPassword.setText(DEFAULT_PASS);
        }
        mRpcHostAddress.setText(mUserPreferences.getRpcHostAddress());
        mRpcHostPort.setText(mUserPreferences.getRpcHostPort());
        mRpcDefaultFolder.setText(mUserPreferences.getRpcDefaultFolder());
    }
}
