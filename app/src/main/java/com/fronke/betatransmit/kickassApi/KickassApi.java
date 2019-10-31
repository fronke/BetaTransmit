package com.fronke.betatransmit.kickassApi;


import com.fronke.betatransmit.utils.Request;
import com.fronke.betatransmit.utils.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Kevin on 21/09/2015.
 */
public class KickassApi {

    private String mTorrentHost = "https://kat.cr/json.php?q=";

    public String[] getTorrentLinks(String searchTerms) {

        // Remove ( )
        int open = searchTerms.indexOf('(');
        int close = searchTerms.indexOf(')');
        if (open != -1 && close != -1) {
            searchTerms = searchTerms.substring(0, open) +  searchTerms.substring(close+1, searchTerms.length());
        }

        searchTerms = searchTerms.replaceAll(" ", "%20");

        String url = mTorrentHost + searchTerms;

        Response response = Request.Get(url);


        if (response.isSuccess()) {
            return parseJsonForLinks(response.getContent());
        }

        return null;
    }

    public String[] parseJsonForLinks(String jsonString) {


        try {
            JSONObject jObjRoot = new JSONObject(jsonString);

            if (!jObjRoot.isNull("list")) {
                JSONArray jObjList = jObjRoot.getJSONArray("list");

                if (jObjList.length() > 0 ) {
                    String[] links = new String[3];
                    for (int i = 0; i < 3; i++) {
                        if (!jObjList.isNull(i)) {
                            links[i] = jObjList.getJSONObject(i).getString("torrentLink");
                        }
                    }
                    return links;
                }
            }
        }
        catch (JSONException e) {}

        return null;
    }
}
