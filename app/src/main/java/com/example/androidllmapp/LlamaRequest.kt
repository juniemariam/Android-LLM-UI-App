package com.example.androidllmapp

data class LlamaRequest(
    val model: String = "meta-llama/Llama-3-70B-Instruct-Turbo",
    val messages: List<Message>
)

data class Message(
    val role: String,
    val content: String
)
