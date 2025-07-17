package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.miroslava958.culinarycompanion.databinding.ActivityAddRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.launch

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    /* ViewModel obtained via the single-argument factory */
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Shared intent values
        val isEditMode = intent.getBooleanExtra("EDIT_MODE", false)
        val existingId = intent.getIntExtra("RECIPE_ID", -1)
        Log.d("AddRecipeActivity", "Editing recipe with ID: $existingId")

        val titleFromIntent = intent.getStringExtra("RECIPE_TITLE") ?: ""
        val ingredientsFromIntent = intent.getStringExtra("RECIPE_INGREDIENTS") ?: ""
        val instructionsFromIntent = intent.getStringExtra("RECIPE_INSTRUCTIONS") ?: ""
        val categoryFromIntent = intent.getStringExtra("RECIPE_CATEGORY") ?: "Others"

        if (isEditMode) {
            // Pre-fill form with existing recipe data
            binding.recipeNameInput.setText(titleFromIntent)
            binding.ingredientsInput.setText(ingredientsFromIntent)
            binding.instructionsInput.setText(instructionsFromIntent)

            // Set the correct category in spinner
            val adapter = binding.categorySpinner.adapter
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i)?.toString() == categoryFromIntent) {
                    binding.categorySpinner.setSelection(i)
                    break
                }
            }

            binding.saveButton.text = getString(com.miroslava958.culinarycompanion.R.string.update)
        }

        // Cancel (X) button
        binding.cancelButton.setOnClickListener { finish() }

        // Save button
        binding.saveButton.setOnClickListener {
            val title = binding.recipeNameInput.text.toString().trim()
            val ingredients = binding.ingredientsInput.text.toString().trim()
            val instructions = binding.instructionsInput.text.toString().trim()
            val category = binding.categorySpinner.selectedItem.toString()

            if (title.isBlank() || ingredients.isBlank() || instructions.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                val isDuplicate = viewModel.isDuplicateRecipeTitle(title)
                val isSameAsExisting = isEditMode && viewModel.getRecipeById(existingId)?.title == title

                if (isDuplicate && !isSameAsExisting) {
                    Toast.makeText(this@AddRecipeActivity, "A recipe with this title already exists.", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val recipe = Recipe(
                    id = if (isEditMode) existingId else 0,
                    title = title,
                    category = category,
                    ingredients = ingredients,
                    instructions = instructions
                )

                if (isEditMode) {
                    viewModel.updateRecipe(recipe)
                    Toast.makeText(this@AddRecipeActivity, "Recipe updated!", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.addRecipe(recipe)
                    Toast.makeText(this@AddRecipeActivity, "Recipe saved!", Toast.LENGTH_SHORT).show()
                }

                finish()
            }
        }
    }
}

