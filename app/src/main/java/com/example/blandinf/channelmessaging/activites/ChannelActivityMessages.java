package com.example.blandinf.channelmessaging.activites;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.adapter.MyArrayAdapter;
import com.example.blandinf.channelmessaging.adapter.MyMessageAdapter;
import com.example.blandinf.channelmessaging.model.Message;
import com.example.blandinf.channelmessaging.response.ChannelReponse;
import com.example.blandinf.channelmessaging.response.MessagesResponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by blandinf on 29/01/2018.
 */
public class ChannelActivityMessages extends Activity{

    private static final String TAG = ChannelListActivity.class.getSimpleName();
    private Gson gson = new Gson();
    public static final String PREFS_NAME = "MyPrefsFile";
    private Handler handler;
    private Bundle bundle;
    private ListView _messages;
    private TextView _message_content;
    private Button _send_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_activity_messages);

        _messages = (ListView) findViewById(R.id.messages);
        _send_button = (Button) findViewById(R.id.send);
        _message_content = (TextView) findViewById(R.id.message_content);

        HashMap<String, String> hm = new HashMap<String, String>();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("access",null);
        int channel_id = settings.getInt("channelid",1);
        hm.put("accesstoken",token);
        hm.put("channelid",Integer.toString(channel_id));

        PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getmessages", hm);
        HttpPostHandler httpPostHandler = new HttpPostHandler();
        httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onDownloadComplete(String downloadedContent) {
                Toast.makeText(getBaseContext(), downloadedContent, Toast.LENGTH_SHORT).show();
                final MessagesResponse response = gson.fromJson(downloadedContent, MessagesResponse.class);
                handler = new Handler();
                Runnable r = new Runnable() {
                    public void run() {
                        _messages.setAdapter(new MyMessageAdapter(getApplicationContext(),response.getMessages()));
                    }
                };
                handler.postDelayed(r, 1000);
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        httpPostHandler.execute(postRequest);

        _send_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                HashMap<String, String> hm = new HashMap<String, String>();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String token = settings.getString("access",null);
                int channel_id = settings.getInt("channelid",1);
                hm.put("accesstoken",token);
                hm.put("channelid",Integer.toString(channel_id));
                hm.put("message",_message_content.getText().toString());


                PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=sendmessage", hm);
                HttpPostHandler httpPostHandler = new HttpPostHandler();
                httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete(String downloadedContent) {
                        Toast.makeText(getBaseContext(), downloadedContent, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDownloadError(String error) {
                        Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
                httpPostHandler.execute(postRequest);
            }
        });

    }
}
