package com.example.suareapp.network;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface ApiService {
    //sending remote messages
    @POST("send")
    Call<String> sendRemoteMessages(
            @HeaderMap HashMap<String,String> headers,
            @Body String remoteBody
            );
}
