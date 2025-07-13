package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.databinding.ActivityAddRecipeBinding

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View-binding
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cancel X-button in the top-left
        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
}