package com.example.blandinf.channelmessaging.model;

/**
 * Created by blandinf on 29/01/2018.
 */
public class Channel {
    private int channelID;
    private String name;
    private int connectedusers;

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getConnectedusers() {
        return connectedusers;
    }

    public void setConnectedusers(int connectedusers) {
        this.connectedusers = connectedusers;
    }

    public Channel(int channelID, String name, int connectedusers) {
        this.channelID = channelID;
        this.name = name;
        this.connectedusers = connectedusers;
    }
}
