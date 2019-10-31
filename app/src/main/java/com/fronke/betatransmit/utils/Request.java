package com.fronke.betatransmit.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;


import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.HostnameVerifier;

public class Request {

    public static Response Post(String query, Object data) {
        return ExecuteHttp("POST", query, data, "");
    }

    public static Response PostJson(String query, Object data) {
        return ExecuteHttp("POST", query, data, "JSON");
    }

    public static Response PostWww(String query, Object data) {
        return ExecuteHttp("POST", query, data, "WWW");
    }

    public static Response Get(String query) {
        return ExecuteHttp("GET", query, null, "");
    }



    private static Response ExecuteHttp(String restType, String query, Object data, String content) {

        StringBuilder builder = new StringBuilder();
        String cookie = "";

        HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

        DefaultHttpClient client = new DefaultHttpClient();

        SchemeRegistry registry = new SchemeRegistry();
        SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
        socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
        registry.register(new Scheme("https", socketFactory, 443));
        SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);

        HttpClient httpclient= new DefaultHttpClient(mgr, client.getParams());
        HttpUriRequest request = null;

        // Query method configuration
        if (restType.equals("GET")) {
            request = new HttpGet(query);
        }
        else if (restType.equals("POST")) {
            request = new HttpPost(query);

            StringEntity input = null;
            input = new StringEntity(((String) data), "UTF-8");

            ((HttpPost) request).setEntity(input);

        }

        if (content.equals("JSON")) {
            request.setHeader("Accept", "application/json, text/json");
            request.setHeader("Content-Type", "application/json");
        }
        else if (content.equals("WWW")) {
            request.setHeader("Content-Type", "application/x-www-form-urlencoded");
        }

        try {
            HttpResponse response = httpclient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {

                // Get content
                HttpEntity entity = response.getEntity();
                InputStream stream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
            else {
                return new Response(null);
            }
        }
        catch (ClientProtocolException e) {
            return new Response(null);
        }
        catch (IOException e) {
            return new Response(null);
        }

        httpclient.getConnectionManager().shutdown();

        return new Response(builder.toString());
    }


}
