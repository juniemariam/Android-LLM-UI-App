package com.example.androidllmapp
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitLlamaClient {
    private const val BASE_URL = "https://api.together.xyz/"
    private const val API_KEY = "" // Replace with your actual key

    fun getClient(): LlamaService {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val newRequest = original.newBuilder()
                    .addHeader("Authorization", "Bearer $API_KEY")
                    .build()
                chain.proceed(newRequest)
            }
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LlamaService::class.java)
    }
}
