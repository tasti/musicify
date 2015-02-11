package com.zakarie.musicify.api.request;

public class PutUserSuggestionRequest {

    public String spotify_id;

    public PutUserSuggestionRequest(String spotify_id) {
        this.spotify_id = spotify_id;
    }

}
