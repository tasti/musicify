package com.zakarie.musicify.api;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface MusicifyService {

    public static final String MUSICIFY_API = "http://musicify-app.appspot.com";

    @GET("/api/v1/users/{username}")
    void getUser(@Path("username") String username, Callback<MusicifyResponse> cb);

    @POST("/api/v1/users")
    void postUser(@Body MusicifyRequest request, Callback<MusicifyResponse> cb);

}
