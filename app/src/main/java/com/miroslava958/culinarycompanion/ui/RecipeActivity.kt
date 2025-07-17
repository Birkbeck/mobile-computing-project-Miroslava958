package com.miroslava958.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.miroslava958.culinarycompanion.MainActivity
import com.miroslava958.culinarycompanion.databinding.ActivityRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel
import androidx.activity.viewModels
import kotlinx.coroutines.launch

class RecipeActivity : AppCompatActivity() {

    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }
    private lateinit var binding: ActivityRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Receive passed data from intent
        val title = intent.getStringExtra("RECIPE_TITLE") ?: "Untitled"
        val ingredients = intent.getStringExtra("RECIPE_INGREDIENTS") ?: "No ingredients provided."
        val instructions = intent.getStringExtra("RECIPE_INSTRUCTIONS") ?: "No instructions provided."
        val category = intent.getStringExtra("RECIPE_CATEGORY") ?: "Others"
        val recipeId = intent.getIntExtra("RECIPE_ID", -1)

        // Bind data to layout
        binding.recipeTitle.text = title
        binding.ingredients.text = ingredients
        binding.instructions.text = instructions

        // Navigation buttons
        binding.btnBack.setOnClickListener { finish() }

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        binding.btnMainAdd.setOnClickListener {
            val intent = Intent(this, AddRecipeActivity::class.java)
            startActivity(intent)
        }

        // Edit button click
        binding.btnEdit.setOnClickListener {
            Log.d("RecipeActivity", "Editing recipe ID: $recipeId")
            val intent = Intent(this, AddRecipeActivity::class.java)
            intent.putExtra("EDIT_MODE", true)
            intent.putExtra("RECIPE_ID", recipeId)
            intent.putExtra("RECIPE_TITLE", title)
            intent.putExtra("RECIPE_INGREDIENTS", ingredients)
            intent.putExtra("RECIPE_INSTRUCTIONS", instructions)
            intent.putExtra("RECIPE_CATEGORY", category)
            startActivity(intent)
        }

        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes") { _, _ ->
                    lifecycleScope.launch {
                        // Optional: retrieve from DB to ensure it's the latest version
                        val recipe = Recipe(
                            id = recipeId,
                            title = binding.recipeTitle.text.toString(),
                            ingredients = binding.ingredients.text.toString(),
                            instructions = binding.instructions.text.toString(),
                            category = intent.getStringExtra("RECIPE_CATEGORY") ?: "Others"
                        )
                        viewModel.deleteRecipe(recipe)
                        Toast.makeText(this@RecipeActivity, "Recipe deleted", Toast.LENGTH_SHORT).show()

                        // Navigate back to category
                        val intent = Intent(this@RecipeActivity, CategoriesTemplateActivity::class.java)
                        intent.putExtra("CATEGORY_NAME", recipe.category)
                        startActivity(intent)
                        finish()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

    }
}