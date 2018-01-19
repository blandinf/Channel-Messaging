package com.example.blandinf.channelmessaging;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by blandinf on 19/01/2018.
 */
public class HttpPostHandler extends AsyncTask<PostRequest,Long,String>{
    ArrayList<OnDownloadListener> listeners = new ArrayList<>();

    @Override
    protected String doInBackground(PostRequest... params) {
        PostRequest postrequest = params[0];
        return performPostCall(postrequest.getUrl(),postrequest.getPostParams());
    }

     public void addOnDownloadListener(OnDownloadListener listener){
        listeners.add(listener);
     }

    public String performPostCall(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        String response = "";
        try
        {
            url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode =conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br =new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else
            {
                response ="";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        for (OnDownloadListener oneListener : listeners)
        {
            oneListener.onDownloadComplete(s);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    public void newsDownloadCompleted(String myNews){
    }

    public void newsDownloadError(String error){
        for (OnDownloadListener oneListener : listeners)
        {
            oneListener.onDownloadError(error);
        }
    }
}
