package com.example.blandinf.channelmessaging.ws;

import java.util.HashMap;

/**
 * Created by blandinf on 19/01/2018.
 */
public class PostRequest {
    private String url;
    private HashMap<String,String> postParams;

    public PostRequest(String url, HashMap<String, String> postParams) {

        this.url = url;
        this.postParams = postParams;
    }

    public String getUrl() {
        return url;
    }

    public HashMap<String, String> getPostParams() {
        return postParams;
    }
}
