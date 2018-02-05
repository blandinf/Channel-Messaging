package com.example.blandinf.channelmessaging.response;

/**
 * Created by blandinf on 05/02/2018.
 */
public class SendResponse {

    private String response;
    private int code;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SendResponse(String response, int code) {
        this.response = response;
        this.code = code;
    }
}
