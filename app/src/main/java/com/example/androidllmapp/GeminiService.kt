package com.example.androidllmapp

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GeminiService {
    @POST("v1/models/gemini-1.5-pro:generateContent")
    fun generateText(@Body request: GeminiRequest): Call<GeminiResponse>
}
