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

class CategoriesTemplateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesTemplateBinding
    private lateinit var adapter: RecipeAdapter

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriesTemplateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // extras
        val category   = intent.getStringExtra("CATEGORY_NAME") ?: "All"
        val searchText = intent.getStringExtra("SEARCH_QUERY") ?: ""

        binding.categoryTitle.text =
            if (searchText.isBlank()) category else "Results for \"$searchText\""

        // adapter
        adapter = RecipeAdapter(emptyList()) { recipe ->
            val intent = Intent(this, RecipeActivity::class.java)
            intent.putExtra("RECIPE_TITLE", recipe.title)
            intent.putExtra("RECIPE_INGREDIENTS", recipe.ingredients)
            intent.putExtra("RECIPE_INSTRUCTIONS", recipe.instructions)
            startActivity(intent)
        }

        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeRecyclerView.adapter = adapter

        // observe DB
        val liveData =
            if (category == "All") viewModel.allRecipes
            else viewModel.recipesByCategory(category)

        liveData.observe(this) { all ->
            val shown = if (searchText.isBlank()) all
            else all.filter { it.title.contains(searchText, true) }

            adapter.updateRecipes(shown)
            binding.emptyHint.visibility =
                if (shown.isEmpty()) android.view.View.VISIBLE
                else android.view.View.GONE
        }

        // Home
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // ADD button
        binding.btnMainAdd.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish() // closes this screen and returns to previous one
        }
    }
}