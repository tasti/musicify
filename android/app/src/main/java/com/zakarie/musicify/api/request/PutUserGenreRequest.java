package com.zakarie.musicify.api.request;

import com.zakarie.musicify.api.request.MusicifyRequest;

public class PutUserGenreRequest extends MusicifyRequest {

    public String genre;

    public PutUserGenreRequest(String genre) {
        this.genre = genre;
    }

}
