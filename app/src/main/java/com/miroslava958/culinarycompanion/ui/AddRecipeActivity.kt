package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.data.AppDatabase
import com.miroslava958.culinarycompanion.data.RecipeRepository
import com.miroslava958.culinarycompanion.databinding.ActivityAddRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    /* ViewModel */
    private val viewModel: RecipeViewModel by viewModels {
        val dao = AppDatabase.getInstance(this).recipeDao()
        RecipeViewModel.provideFactory(RecipeRepository(dao))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View-binding
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cancel X-button in the top-left
        binding.cancelButton.setOnClickListener {
            finish()
        }

        // Save
        binding.saveButton.setOnClickListener {
            val title = binding.recipeName.text.toString().trim()
            val ingredients = binding.ingredients.text.toString().trim()
            val instructions = binding.instructions.text.toString().trim()
            val category = binding.categorySpinner.selectedItem.toString()

            if (title.isBlank() || ingredients.isBlank() || instructions.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val recipe = Recipe(title = title,
                category = category,
                ingredients = ingredients,
                instructions = instructions)

            viewModel.addRecipe(recipe)                // ← stored in Room
            Toast.makeText(this, "Recipe saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}