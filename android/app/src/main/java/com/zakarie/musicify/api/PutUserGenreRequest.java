package com.zakarie.musicify.api;

public class PutUserGenreRequest extends MusicifyRequest {

    public String genre;

    public PutUserGenreRequest(String genre) {
        this.genre = genre;
    }

}
