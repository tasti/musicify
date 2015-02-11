package com.zakarie.musicify.api;

import com.zakarie.musicify.api.request.PostUserRequest;
import com.zakarie.musicify.api.request.PutUserGenreRequest;
import com.zakarie.musicify.api.request.PutUserSuggestionRequest;
import com.zakarie.musicify.api.response.GetGenresResponse;
import com.zakarie.musicify.api.response.GetRelatedResponse;
import com.zakarie.musicify.api.response.GetSuggestionsResponse;
import com.zakarie.musicify.api.response.GetTracksResponse;
import com.zakarie.musicify.api.response.GetUserResponse;
import com.zakarie.musicify.api.response.MusicifyResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface MusicifyService {

    public static final String MUSICIFY_API = "http://musicify-app.appspot.com";
    public static final String API_V1 = "/api/v1";

    @GET(API_V1 + "/users/{username}")
    void getUser(@Path("username") String username, Callback<GetUserResponse> cb);

    @POST(API_V1 + "/users")
    void postUser(@Body PostUserRequest request, Callback<MusicifyResponse> cb);

    @PUT(API_V1 + "/users/{username}/genre")
    void putUserGenre(@Path("username") String username, @Body PutUserGenreRequest request, Callback<MusicifyResponse> cb);

    @GET(API_V1 + "/users/{username}/suggestions")
    void getSuggestions(@Path("username") String username, Callback<GetSuggestionsResponse> cb);

    @PUT(API_V1 + "/users/{username}/suggestions")
    void putSuggestion(@Path("username") String username, @Body PutUserSuggestionRequest request, Callback<MusicifyResponse> cb);

    @GET(API_V1 + "/genres")
    void getGenres(Callback<GetGenresResponse> cb);

    @GET(API_V1 + "/tracks/artist/{spotify_id}")
    void getTracks(@Path("spotify_id") String spotify_id, Callback<GetTracksResponse> cb);

    @GET(API_V1 + "/artists/related/{spotify_id}")
    void getRelated(@Path("spotify_id") String spotify_id, Callback<GetRelatedResponse> cb);

}
