package com.miroslava958.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.MainActivity
import com.miroslava958.culinarycompanion.databinding.ActivityViewCategoriesBinding

/**
 * The screen presents buttons for each recipe category.
 * When a button is clicked, it opens the list of recipes in that category.
 */
class ViewCategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Category buttons
        binding.btnBreakfast.setOnClickListener { openCategory("Breakfast") }
        binding.btnLunch.setOnClickListener { openCategory("Lunch") }
        binding.btnDessert.setOnClickListener { openCategory("Dessert") }
        binding.btnDinner.setOnClickListener { openCategory("Dinner") }
        binding.btnBrunch.setOnClickListener { openCategory("Brunch") }
        binding.btnOthers.setOnClickListener { openCategory("Others") }

        // Home button
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Add new recipe with default category: Other
        binding.btnMainAdd.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            intent.putExtra("RECIPE_CATEGORY", "Others")
            startActivity(intent)
        }

        // Back button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    /**
     * Opens the recipe list for the selected category.
     */
    private fun openCategory(category: String) {
        val intent = Intent(this, CategoriesTemplateActivity::class.java)
        intent.putExtra("CATEGORY_NAME", category)
        startActivity(intent)
    }
}