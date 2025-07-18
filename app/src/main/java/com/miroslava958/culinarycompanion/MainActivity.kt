package com.miroslava958.culinarycompanion

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.databinding.ActivityMainBinding
import com.miroslava958.culinarycompanion.ui.AddRecipeActivity
import com.miroslava958.culinarycompanion.ui.CategoriesTemplateActivity
import com.miroslava958.culinarycompanion.ui.ViewCategoriesActivity

/**
 * This is the main screen of the app.
 * Users can add a new recipe, view recipes by category, or search by recipe name.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up view binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Add recipe button, starts AddRecipeActivity
        binding.btnMainAdd.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            intent.putExtra("RECIPE_CATEGORY", "Others") // Default category
            startActivity(intent)
        }

        // View categories button, shows all categories
        binding.btnMainView.setOnClickListener {
            startActivity(Intent(this, ViewCategoriesActivity::class.java))
        }

        // Search button, opens search input dialog
        binding.btnSearch.setOnClickListener { showSearchDialog() }
    }

    /**
     * Shows a dialog with a text input for searching recipes.
     */
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

    /**
     * Opens the recipe list screen with search results.
     * It sends the search query to the next screen.
     */
    private fun openResultsScreen(query: String) {
        val intent = Intent(this, CategoriesTemplateActivity::class.java).apply {
            putExtra("CATEGORY_NAME", "All")
            putExtra("SEARCH_QUERY", query)
        }
        startActivity(intent)
    }
}