package com.example.blandinf.channelmessaging.response;

import com.example.blandinf.channelmessaging.model.Channel;

import java.util.ArrayList;

/**
 * Created by blandinf on 29/01/2018.
 */
public class ChannelReponse {
    private ArrayList<Channel> channels;

    public ArrayList<Channel> getChannels() {
        return channels;
    }

    public void setChannels(ArrayList<Channel> channels) {
        this.channels = channels;
    }

    public ChannelReponse(ArrayList<Channel> channels) {
        this.channels = channels;
    }
}
