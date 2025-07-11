package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miroslava958.culinarycompanion.adapter.RecipeAdapter
import com.miroslava958.culinarycompanion.databinding.ActivityCategoriesTemplateBinding
import com.miroslava958.culinarycompanion.model.Recipe

class CategoriesTemplateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesTemplateBinding
    private lateinit var adapter: RecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("CategoriesActivity", "onCreate started")

        binding = ActivityCategoriesTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the category from the Intent
        val categoryName = intent.getStringExtra("CATEGORY_NAME") ?: "All"
        Log.d("CategoriesTemplate", "Received category: $categoryName")

        // Set the title at the top
        binding.categoryTitle.text = categoryName

        // Dummy recipe data
        val sampleRecipes = listOf(
            Recipe("Spaghetti", "Lunch"),
            Recipe("Pancakes", "Breakfast"),
            Recipe("Chocolate Cake", "Dessert"),
            Recipe("Salad", "Lunch"),
            Recipe("Toast", "Breakfast")
        )

        // Filter recipes based on the selected category
        val filteredRecipes = sampleRecipes.filter {
            it.category.equals(categoryName, ignoreCase = true)
        }

        // Log to test
        Log.d("CategoriesTemplate", "Intent category: $categoryName")
        Log.d("CategoriesTemplate", "Filtered recipes: ${filteredRecipes.size}")
        filteredRecipes.forEach {
            Log.d("CategoriesTemplate", "Recipe: ${it.title}")
        }
        // Initialize adapter
        adapter = RecipeAdapter(filteredRecipes) { recipe ->
            Toast.makeText(this, "Clicked: ${recipe.title}", Toast.LENGTH_SHORT).show()
        }

        // Setup RecyclerView
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeRecyclerView.adapter = adapter

        Log.d("AdapterTest", "Recipes loaded: ${filteredRecipes.size}")
    }
}