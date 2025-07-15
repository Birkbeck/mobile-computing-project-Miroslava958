package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.miroslava958.culinarycompanion.adapter.RecipeAdapter
import com.miroslava958.culinarycompanion.databinding.ActivityCategoriesTemplateBinding
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel

class CategoriesTemplateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesTemplateBinding
    private lateinit var adapter: RecipeAdapter

    /* ViewModel – built with the single-argument factory */
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

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

        /* ---------- RecyclerView setup ---------- */
        adapter = RecipeAdapter(emptyList()) { recipe ->
            Toast.makeText(this, "Clicked: ${recipe.title}", Toast.LENGTH_SHORT).show()
        }
        binding.recipeRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.recipeRecyclerView.adapter = adapter

        /* ---------- Observe Room data ---------- */
        val liveData =
            if (categoryName == "All") viewModel.allRecipes
            else viewModel.recipesByCategory(categoryName)

        liveData.observe(this) { list ->
            Log.d("CategoriesTemplate", "Loaded ${list.size} recipes")
            adapter.updateRecipes(list)            // refresh adapter
            binding.emptyHint.visibility =
                if (list.isEmpty()) android.view.View.VISIBLE
                else android.view.View.GONE
        }
    }
}