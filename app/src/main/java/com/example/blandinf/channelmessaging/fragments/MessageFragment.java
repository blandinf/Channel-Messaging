package com.example.blandinf.channelmessaging.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.activites.DetailActivity;
import com.example.blandinf.channelmessaging.adapter.MyMessageAdapter;
import com.example.blandinf.channelmessaging.response.MessagesResponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by blandinf on 26/02/2018.
 */
public class MessageFragment extends Fragment {
    private ListView _messages;
    private Button _send_button;
    private TextView _message_content;
    public static final String PREFS_NAME = "MyPrefsFile";
    private Gson gson = new Gson();
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.message_fragment,container);

        _messages = (ListView) v.findViewById(R.id.messages);
        _send_button = (Button) v.findViewById(R.id.send);
        _message_content = (TextView) v.findViewById(R.id.message_content);

        _send_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMessages();
    }

    public void getMessages( ) {
        HashMap<String, String> hm = new HashMap<String, String>();
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = settings.getString("access", null);
        Long channel_id = settings.getLong("channelid",2);
        hm.put("accesstoken", token);
        hm.put("channelid", Long.toString(channel_id));

        PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", hm);
        HttpPostHandler httpPostHandler = new HttpPostHandler();
        httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onDownloadComplete(String downloadedContent) {
                Toast.makeText(getContext(), downloadedContent, Toast.LENGTH_SHORT).show();
                final MessagesResponse response = gson.fromJson(downloadedContent, MessagesResponse.class);
                handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        _messages.setAdapter(new MyMessageAdapter(getContext(), response.getMessages()));
                    }
                };
                handler.postDelayed(r, 1000);
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        httpPostHandler.execute(postRequest);
    }

    public void sendMessage(){
        HashMap<String, String> hm = new HashMap<String, String>();
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String token = settings.getString("access",null);
        Long channel_id = settings.getLong("channelid",1);
        hm.put("accesstoken",token);
        hm.put("channelid",Long.toString(channel_id));
        hm.put("message",_message_content.getText().toString());


        PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=sendmessage", hm);
        HttpPostHandler httpPostHandler = new HttpPostHandler();
        httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onDownloadComplete(String downloadedContent) {
                Toast.makeText(getContext(), downloadedContent, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        httpPostHandler.execute(postRequest);
    }

}