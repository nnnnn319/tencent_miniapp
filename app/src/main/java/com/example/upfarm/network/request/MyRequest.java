
package com.example.upfarm.network.request;

import okhttp3.OkHttpClient;

public class MyRequest {
    private String url = "http://192.168.0.106:8080";
    private OkHttpClient okHttpClient;
    public MyRequest() {
        okHttpClient = new OkHttpClient();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }
}

