package com.sanrenxing.shop.util.http;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class OKHttpUtil {

    private static OkHttpClient client;

    private static final int TIME_OUT = 20;

    static {
        client = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    public static String request(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String post(Headers headers, String url, String body) throws IOException {
        RequestBody requestBody = RequestBody.create(null, body);
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
