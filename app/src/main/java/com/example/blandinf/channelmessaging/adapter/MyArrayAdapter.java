package com.example.blandinf.channelmessaging.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.model.Channel;

import java.util.ArrayList;

/**
 * Created by blandinf on 29/01/2018.
 */
public class MyArrayAdapter extends ArrayAdapter<Channel>{

    private Context context;
    private ArrayList<Channel> channels;

    public MyArrayAdapter(Context context, ArrayList<Channel> channels){
        super(context, R.layout.channel_activity,channels);
        this.context = context;
        this.channels = channels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.channel_activity, parent, false);

        Channel channel = channels.get(position);

        TextView channel_title = (TextView) rowView.findViewById(R.id.channel_title);
        TextView channel_content = (TextView) rowView.findViewById(R.id.channel_content);

        channel_title.setText(channel.getName());
        channel_content.setText(channel.getConnectedusers() + " utilisateurs connectés");

        return rowView;
    }
}
