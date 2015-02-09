package com.zakarie.musicify.api;

public class PostUserRequest extends MusicifyRequest {

    public String username;

    public PostUserRequest(String username) {
        this.username = username;
    }

}
