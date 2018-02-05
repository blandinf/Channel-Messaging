package com.example.blandinf.channelmessaging.model;

import java.util.UUID;

/**
 * Created by blandinf on 05/02/2018.
 */

public class User {
    private UUID userID;
    private String username;
    private String imageURL;

    public UUID getUserID() {
        return userID;
    }

    public void setUserID(UUID userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public User(UUID userID, String username, String imageURL) {
        this.userID = userID;
        this.username = username;
        this.imageURL = imageURL;
    }
}
