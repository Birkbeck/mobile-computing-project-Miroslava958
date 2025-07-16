package com.miroslava958.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.databinding.ActivityMainBinding
import com.miroslava958.culinarycompanion.ui.AddRecipeActivity
import com.miroslava958.culinarycompanion.ui.CategoriesTemplateActivity
import com.miroslava958.culinarycompanion.ui.ViewCategoriesActivity
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ViewModel for later
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Buttons
        binding.btnMainAdd.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }
        binding.btnMainView.setOnClickListener {
            startActivity(Intent(this, ViewCategoriesActivity::class.java))
        }
        binding.btnSearch.setOnClickListener { showSearchDialog() }      // 🔍
    }

    // search
    private fun showSearchDialog() {
        val input = EditText(this)
        AlertDialog.Builder(this)
            .setTitle("Search recipes")
            .setView(input)
            .setPositiveButton("Search") { _, _ ->
                val q = input.text.toString().trim()
                if (q.isNotEmpty()) openResultsScreen(q)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun openResultsScreen(query: String) {
        val intent = Intent(this, CategoriesTemplateActivity::class.java).apply {
            putExtra("CATEGORY_NAME", "All")
            putExtra("SEARCH_QUERY",  query)
        }
        startActivity(intent)
    }
}