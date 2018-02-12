package com.example.blandinf.channelmessaging.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.model.Channel;
import com.example.blandinf.channelmessaging.model.Message;
import com.example.blandinf.channelmessaging.model.User;

import java.util.ArrayList;

/**
 * Created by blandinf on 12/02/2018.
 */
public class MyFriendsListAdapter extends ArrayAdapter<User> {

    private Context context;
    private ArrayList<User> users;

    public MyFriendsListAdapter(Context context, ArrayList<User> users){
        super(context, R.layout.friends_row_activity,users);
        this.context = context;
        this.users = users;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.friends_row_activity, parent, false);

        User user = users.get(position);

        TextView _username = (TextView) rowView.findViewById(R.id.username);
        TextView _imageURL = (TextView) rowView.findViewById(R.id.imageURL);

        _username.setText(user.getUsername());
        _imageURL.setText(user.getImageURL());

        return rowView;
    }
}
