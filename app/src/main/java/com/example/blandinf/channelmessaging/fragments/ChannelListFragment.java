package com.example.blandinf.channelmessaging.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.activites.FriendsListActivity;
import com.example.blandinf.channelmessaging.activites.MainActivity;
import com.example.blandinf.channelmessaging.adapter.MyArrayAdapter;
import com.example.blandinf.channelmessaging.response.ChannelReponse;
import com.example.blandinf.channelmessaging.ws.HttpPostHandler;
import com.example.blandinf.channelmessaging.ws.OnDownloadListener;
import com.example.blandinf.channelmessaging.ws.PostRequest;
import com.google.gson.Gson;

import java.util.HashMap;

/**
 * Created by blandinf on 26/02/2018.
 */
public class ChannelListFragment extends Fragment {
    private Button _friends_button;
    private ListView _listView;
    private static final String TAG = "MyTag";
    private Gson gson = new Gson();
    public static final String PREFS_NAME = "MyPrefsFile";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.channel_list_fragment,container);

        _friends_button = (Button)v.findViewById(R.id.friends_button);
        _friends_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),FriendsListActivity.class);
                startActivity(intent);
            }
        });

        _listView = (ListView)v.findViewById(R.id.listView);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getChannels();
    }

    public void getChannels(){
        HashMap<String, String> hm = new HashMap<String, String>();
        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        String token = settings.getString("access",null);
        Log.e(TAG, "token :" + token);
        hm.put("accesstoken",token);
        PostRequest postRequest = new PostRequest("http://www.raphaelbischof.fr/messaging/?function=getchannels", hm);
        HttpPostHandler httpPostHandler = new HttpPostHandler();
        httpPostHandler.addOnDownloadListener(new OnDownloadListener() {
            @Override
            public void onDownloadComplete(String downloadedContent) {
                Toast.makeText(getActivity(), downloadedContent, Toast.LENGTH_SHORT).show();
                ChannelReponse channels = gson.fromJson(downloadedContent, ChannelReponse.class);

                _listView.setAdapter(new MyArrayAdapter(getContext(),channels.getChannels()));
                _listView.setOnItemClickListener((MainActivity)getActivity());
            }

            @Override
            public void onDownloadError(String error) {
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            }
        });
        httpPostHandler.execute(postRequest);
    }
}
