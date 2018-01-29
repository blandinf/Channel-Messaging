package com.example.blandinf.channelmessaging.ws;

/**
 * Created by blandinf on 19/01/2018.
 */
public interface OnDownloadListener {
    public void onDownloadComplete(String downloadedContent);
    public void onDownloadError(String error);
}
