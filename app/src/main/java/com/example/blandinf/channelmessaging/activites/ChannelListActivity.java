package com.example.blandinf.channelmessaging.activites;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.blandinf.channelmessaging.model.Channel;
import com.example.blandinf.channelmessaging.model.Message;
import com.example.blandinf.channelmessaging.response.ChannelReponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.adapter.MyArrayAdapter;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.example.blandinf.channelmessaging.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by blandinf on 22/01/2018.
 */
public class ChannelListActivity extends Activity implements AdapterView.OnItemClickListener {

    private static final String TAG = "MyTag";
    private Gson gson = new Gson();
    public static final String PREFS_NAME = "MyPrefsFile";
    private ListView _listView;
    private Button _friends_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list_activity);

        _friends_button = (Button) findViewById(R.id.friends_button);
        _friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FriendsListActivity.class);
                startActivity(intent);
            }
        });

        HashMap<String, String> hm = new HashMap<String, String>();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String token = settings.getString("access",null);
        Log.e(TAG, "token :" + token);
        hm.put("accesstoken",token);
        PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getchannels", hm);
        HttpPostHandler httpPostHandler = new HttpPostHandler();
        httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onDownloadComplete(String downloadedContent) {
                Toast.makeText(getBaseContext(), downloadedContent, Toast.LENGTH_SHORT).show();
                ChannelReponse channels = gson.fromJson(downloadedContent, ChannelReponse.class);

                _listView = (ListView)findViewById(R.id.listView);
                _listView.setAdapter(new MyArrayAdapter(getApplicationContext(),channels.getChannels()));
                _listView.setOnItemClickListener(ChannelListActivity.this);
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        httpPostHandler.execute(postRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getApplicationContext(), ChannelActivityMessages.class);
        intent.putExtra("channelid",position);
        startActivity(intent);
    }
}
