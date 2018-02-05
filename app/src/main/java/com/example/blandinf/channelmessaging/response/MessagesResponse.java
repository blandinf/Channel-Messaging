package com.example.blandinf.channelmessaging.response;

import android.widget.TextView;

import com.example.blandinf.channelmessaging.model.Message;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by blandinf on 22/01/2018.
 */
public class MessagesResponse {

    private ArrayList<Message> messages;

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public MessagesResponse(ArrayList<Message> messages) {
        this.messages = messages;
    }
}
