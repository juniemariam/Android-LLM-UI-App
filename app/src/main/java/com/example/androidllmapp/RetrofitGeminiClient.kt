package com.example.androidllmapp
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitGeminiClient {

    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    private const val API_KEY = "" // Replace with your actual Gemini API key

    fun getClient(): GeminiService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()

                // ✅ Use .url() method to access HttpUrl
                val newUrl = original.url().newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val newRequest = original.newBuilder()
                    .url(newUrl)
                    .build()

                chain.proceed(newRequest)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiService::class.java)
    }
}
