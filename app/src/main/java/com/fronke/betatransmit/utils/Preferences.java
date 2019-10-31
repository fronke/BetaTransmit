package com.fronke.betatransmit.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Kevin on 22/09/2015.
 */
public class Preferences  {

    private final static String PREFERENCE_FILE_KEY = "com.fronke.betatransmit.preferences524354";

    private final static String PREFERENCE_KAT_SEARCH_PARAMETER = "kat_search_parameter";
    private final static String PREFERENCE_BS_USERNAME = "bs_username";
    private final static String PREFERENCE_BS_PASSWORD = "bs_password";
    private final static String PREFERENCE_RPC_USERNAME = "rpc_username";
    private final static String PREFERENCE_RPC_PASSWORD = "rpc_password";
    private final static String PREFERENCE_RPC_HOST_ADDRESS = "rpc_host_address";
    private final static String PREFERENCE_RPC_HOST_PORT = "rpc_host_port";
    private final static String PREFERENCE_RPC_DEFAULT_FOLDER = "rpc_default_folder";

    private Context mContext;

    public Preferences(Context context) {
       this.mContext = context;
    }

    public void setKatSearchParameter(String katSearchParameter, int index) {
        setPreference(PREFERENCE_KAT_SEARCH_PARAMETER+index, katSearchParameter);
    }

    public String[] getKatSearchParameter() {
        String[] values = {
                getPreference(PREFERENCE_KAT_SEARCH_PARAMETER+"1"),
                getPreference(PREFERENCE_KAT_SEARCH_PARAMETER+"2"),
                getPreference(PREFERENCE_KAT_SEARCH_PARAMETER+"3")
        };
        return values;
    }

    public void setRpcUsername(String rpcUsername) {
        setPreference(PREFERENCE_RPC_USERNAME, rpcUsername);
    }

    public String getRpcUsername() {
        return getPreference(PREFERENCE_RPC_USERNAME);
    }

    public void setRpcPassword(String rpcPassword) {
        setPreference(PREFERENCE_RPC_PASSWORD, rpcPassword);
    }

    public String getRpcPassword() {
        return getPreference(PREFERENCE_RPC_PASSWORD);
    }

    public void setRpcHostAddress(String rpcHostAddress) {
        setPreference(PREFERENCE_RPC_HOST_ADDRESS, rpcHostAddress);
    }

    public String getRpcHostAddress() {
        return getPreference(PREFERENCE_RPC_HOST_ADDRESS);
    }

    public void setRpcHostPort(String rpcHostPort) {
        setPreference(PREFERENCE_RPC_HOST_PORT, rpcHostPort);
    }

    public String getRpcHostPort() {
        return getPreference(PREFERENCE_RPC_HOST_PORT);
    }

    public void setRpcDefaultFolder(String rpcDefaultFolder) {
        setPreference(PREFERENCE_RPC_DEFAULT_FOLDER, rpcDefaultFolder);
    }

    public String getRpcDefaultFolder() {
        return getPreference(PREFERENCE_RPC_DEFAULT_FOLDER);
    }

    public void setBsUsername(String bsUsername) {
        setPreference(PREFERENCE_BS_USERNAME, bsUsername);
    }

    public String getBsUsername() {
        return getPreference(PREFERENCE_BS_USERNAME);
    }

    public void setBsPassword(String bsPassword) {
        setPreference(PREFERENCE_BS_PASSWORD, bsPassword);
    }

    public String getBsPassword() {
        return getPreference(PREFERENCE_BS_PASSWORD);
    }

    private void setPreference(String key, String value) {
        SharedPreferences preferences = mContext.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private String getPreference(String key) {
        SharedPreferences preferences = mContext.getSharedPreferences(PREFERENCE_FILE_KEY, Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }
}
