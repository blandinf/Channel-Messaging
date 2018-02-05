package com.example.blandinf.channelmessaging.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.blandinf.channelmessaging.R;
import com.example.blandinf.channelmessaging.model.Channel;
import com.example.blandinf.channelmessaging.model.Message;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by blandinf on 05/02/2018.
 */
public class MyMessageAdapter extends ArrayAdapter<Message>{


        private Context context;
        private ArrayList<Message> messages;

        public MyMessageAdapter(Context context, ArrayList<Message> messages){
            super(context, R.layout.channel_activity_message,messages);
            this.context = context;
            this.messages = messages;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.channel_activity_message, parent, false);

            Message message = messages.get(position);

            TextView username = (TextView) rowView.findViewById(R.id.username);
            TextView message_content = (TextView) rowView.findViewById(R.id.message);
            TextView date = (TextView) rowView.findViewById(R.id.date);

            username.setText(message.getUsername()  + " : ");
            message_content.setText(message.getMessage());
            date.setText(message.getDate());

            return rowView;
        }

    @Override
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
