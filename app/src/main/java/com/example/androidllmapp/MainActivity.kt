package com.example.androidllmapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var promptInput: EditText
    private lateinit var responseView: TextView
    private lateinit var llmSelector: RadioGroup
    private lateinit var sendButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Bind views
        promptInput = findViewById(R.id.promptInput)
        responseView = findViewById(R.id.responseView)
        llmSelector = findViewById(R.id.llmSelector)
        sendButton = findViewById(R.id.sendButton)
        cancelButton = findViewById(R.id.cancelButton)

        // Handle Send button click
        sendButton.setOnClickListener {
            val prompt = promptInput.text.toString()
            val selectedLLM = when (llmSelector.checkedRadioButtonId) {
                R.id.llamaRadio -> "llama"   // ✅ Match the exact string used in LLMService
                R.id.geminiRadio -> "gemini"
                else -> "unknown"
            }

            if (prompt.isNotBlank()) {
                CoroutineScope(Dispatchers.Main).launch {

                    responseView.text = "Generating response..."

                    LLMService.getResponse(prompt, selectedLLM) { result ->
                        runOnUiThread {
                            responseView.text = result
                        }
                    }

                }
            } else {
                Toast.makeText(this, "Please enter a prompt", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle Cancel button click
        cancelButton.setOnClickListener {
            promptInput.text.clear()
            responseView.text = ""
        }
    }
}
