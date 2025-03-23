package com.example.androidllmapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface LlamaService {
    @Headers("Content-Type: application/json")
    @POST("v1/chat/completions")
    fun chat(@Body request: LlamaRequest): Call<LlamaResponse>
}
