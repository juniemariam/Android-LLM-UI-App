package com.example.androidllmapp

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LLMService {

    fun getResponse(prompt: String, provider: String, onResult: (String) -> Unit) {
        when (provider.lowercase()) {
            "gemini" -> callGemini(prompt, onResult)
            "llama" -> callLlama(prompt, onResult)
            else -> onResult("Unknown provider.")
        }
    }

    // ✅ Gemini Integration
    private fun callGemini(prompt: String, onResult: (String) -> Unit) {
        val service = RetrofitGeminiClient.getClient()

        val request = GeminiRequest(
            contents = listOf(
                Content(
                    parts = listOf(
                        Part(text = prompt)
                    )
                )
            )
        )

        service.generateText(request).enqueue(object : Callback<GeminiResponse> {
            override fun onResponse(
                call: Call<GeminiResponse>,
                response: Response<GeminiResponse>
            ) {
                if (response.isSuccessful) {
                    val reply = response.body()
                        ?.candidates?.firstOrNull()
                        ?.content?.parts?.firstOrNull()
                        ?.text ?: "Empty response"
                    onResult(reply)
                } else {
                    onResult("Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GeminiResponse>, t: Throwable) {
                onResult("Network error: ${t.localizedMessage}")
            }
        })
    }

    // ✅ LLaMA (Together API) Integration
    private fun callLlama(prompt: String, onResult: (String) -> Unit) {
        val service = RetrofitLlamaClient.getClient()

        val request = LlamaRequest(
            model = "meta-llama/Llama-3.3-70B-Instruct-Turbo",
            messages = listOf(
                Message(role = "user", content = prompt)
            )
        )

        service.chat(request).enqueue(object : Callback<LlamaResponse> {
            override fun onResponse(call: Call<LlamaResponse>, response: Response<LlamaResponse>) {
                if (response.isSuccessful) {
                    val reply = response.body()
                        ?.choices?.firstOrNull()
                        ?.message?.content ?: "Empty response"
                    onResult(reply)
                } else {
                    onResult("Error: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LlamaResponse>, t: Throwable) {
                onResult("Network error: ${t.localizedMessage}")
            }
        })
    }
}
