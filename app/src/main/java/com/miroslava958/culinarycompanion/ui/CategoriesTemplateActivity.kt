package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
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
        adapter = RecipeAdapter(emptyList()) { r ->
            Toast.makeText(this, r.title, Toast.LENGTH_SHORT).show()
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
    }
}