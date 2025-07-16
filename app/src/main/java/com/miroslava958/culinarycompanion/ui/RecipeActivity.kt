package com.miroslava958.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.MainActivity
import com.miroslava958.culinarycompanion.databinding.ActivityRecipeBinding

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive passed data from intent
        val title = intent.getStringExtra("RECIPE_TITLE") ?: "Untitled"
        val ingredients = intent.getStringExtra("RECIPE_INGREDIENTS") ?: "No ingredients provided."
        val instructions = intent.getStringExtra("RECIPE_INSTRUCTIONS") ?: "No instructions provided."

        // Bind data to layout
        binding.recipeTitle.text = title
        binding.ingredients.text = ingredients
        binding.instructions.text = instructions

        // Navigation buttons
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        binding.btnMainAdd.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }
    }
}
