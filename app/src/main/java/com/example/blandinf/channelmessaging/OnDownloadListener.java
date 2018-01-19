package com.example.blandinf.channelmessaging;

/**
 * Created by blandinf on 19/01/2018.
 */
public interface OnDownloadListener {
    public void onDownloadComplete(String downloadedContent);
    public void onDownloadError(String error);
}
