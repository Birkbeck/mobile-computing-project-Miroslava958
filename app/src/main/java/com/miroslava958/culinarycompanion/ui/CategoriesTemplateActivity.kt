package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import android.util.Log
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

        // Use view binding
        binding = ActivityCategoriesTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Dummy data for testing
        val sampleRecipes = listOf(
            Recipe("Spaghetti", "Lunch"),
            Recipe("Pancakes", "Breakfast"),
            Recipe("Chocolate Cake", "Dessert")
        )

        // Setup RecyclerView
        adapter = RecipeAdapter(sampleRecipes)
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeRecyclerView.adapter = adapter
        Log.d("AdapterTest", "Recipes loaded: ${sampleRecipes.size}")
    }
}