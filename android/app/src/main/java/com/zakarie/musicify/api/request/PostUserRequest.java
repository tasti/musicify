package com.zakarie.musicify.api.request;

import com.zakarie.musicify.api.request.MusicifyRequest;

public class PostUserRequest extends MusicifyRequest {

    public String username;

    public PostUserRequest(String username) {
        this.username = username;
    }

}
