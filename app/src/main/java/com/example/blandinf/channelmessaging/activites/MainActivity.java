package com.example.blandinf.channelmessaging.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.adapter.MyArrayAdapter;
import com.example.blandinf.channelmessaging.adapter.MyMessageAdapter;
import com.example.blandinf.channelmessaging.databases.UserDataSource;
import com.example.blandinf.channelmessaging.fragments.FragmentA;
import com.example.blandinf.channelmessaging.fragments.FragmentB;
import com.example.blandinf.channelmessaging.response.ChannelReponse;
import com.example.blandinf.channelmessaging.response.MessagesResponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by blandinf on 26/02/2018.
 */
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private ListView _listView;
    private Button _friends_button;
    private static final String TAG = "TAG";
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
        setContentView(R.layout.channel_list_fragments);

        _friends_button = (Button) findViewById(R.id.friends_button);
        _friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FriendsListActivity.class);
                startActivity(intent);
            }
        });

        _messages = (ListView) findViewById(R.id.messages);
        _send_button = (Button) findViewById(R.id.send);
        _message_content = (TextView) findViewById(R.id.message_content);

        getChannels();

    }

    public void getChannels(){

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
                _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FragmentA fragA = (FragmentA)getSupportFragmentManager().findFragmentById(R.id.fragmentA_ID);
                        FragmentB fragB = (FragmentB)getSupportFragmentManager().findFragmentById(R.id.fragmentB_ID);
                        if(fragB == null|| !fragB.isInLayout()){
                            Intent i = new Intent(getApplicationContext(),ChannelActivityMessages.class);
                            startActivity(i);
                        } else {
                            getMessages();
                        }
                    }
                });
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(getBaseContext(), error, Toast.LENGTH_SHORT).show();
            }
        });
        httpPostHandler.execute(postRequest);
    }

    public void getMessages(){
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
                Toast.makeText(getApplicationContext(), downloadedContent, Toast.LENGTH_SHORT).show();
                final MessagesResponse response = gson.fromJson(downloadedContent, MessagesResponse.class);
                _messages.setAdapter(new MyMessageAdapter(getApplicationContext(),response.getMessages()));
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

    }
}
