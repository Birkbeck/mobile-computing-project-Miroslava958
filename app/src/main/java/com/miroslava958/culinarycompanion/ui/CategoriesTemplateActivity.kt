package com.miroslava958.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miroslava958.culinarycompanion.MainActivity
import com.miroslava958.culinarycompanion.adapter.RecipeAdapter
import com.miroslava958.culinarycompanion.databinding.ActivityCategoriesTemplateBinding
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel

/**
 * The class shows a list of recipes in a specific category,
 * or filtered search results if a query is passed.
 */
class CategoriesTemplateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesTemplateBinding
    private lateinit var adapter: RecipeAdapter
    private lateinit var category: String
    private lateinit var searchText: String

    // ViewModel gives access to recipes
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get category or search from previous screen
        category = intent.getStringExtra("CATEGORY_NAME") ?: "All"
        searchText = intent.getStringExtra("SEARCH_QUERY") ?: ""

        // Set title based on whether it is a category or search result
        binding.categoryTitle.text =
            if (searchText.isBlank()) category else "Results for \"$searchText\""

        // Set up recipe list and handle item click
        adapter = RecipeAdapter(emptyList()) { recipe ->
            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("RECIPE_ID", recipe.id)
            intent.putExtra("RECIPE_TITLE", recipe.title)
            intent.putExtra("RECIPE_INGREDIENTS", recipe.ingredients)
            intent.putExtra("RECIPE_INSTRUCTIONS", recipe.instructions)
            intent.putExtra("RECIPE_CATEGORY", recipe.category)
            startActivity(intent)
        }

        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeRecyclerView.adapter = adapter

        // Home button: go back to main screen
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Add recipe button: opens AddRecipeActivity with current category selected
        binding.btnMainAdd.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            intent.putExtra("RECIPE_CATEGORY", category)
            startActivity(intent)
        }

        // Back button
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        // Get the right list, all or filtered by category
        val liveData =
            if (category == "All") viewModel.allRecipes
            else viewModel.recipesByCategory(category)

        // Observe and update the list
        liveData.observe(this) { all ->
            val shown = if (searchText.isBlank()) all
            else all.filter { it.title.contains(searchText, true) }

            val sorted = shown.sortedBy { it.title.lowercase() }

            adapter.updateRecipes(sorted)
        }
    }
}