package com.example.blandinf.channelmessaging.model;

/**
 * Created by blandinf on 05/02/2018.
 */
public class Message {
    private int userID;
    private String message;
    private String date;
    private String imageUrl;
    private String username;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Message(int userID, String message, String date, String imageUrl, String username) {
        this.userID = userID;
        this.message = message;
        this.date = date;
        this.imageUrl = imageUrl;
        this.username = username;
    }
}
