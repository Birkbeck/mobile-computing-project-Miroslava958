package com.miroslava958.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.miroslava958.culinarycompanion.databinding.ActivityAddRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * This class lets the user add a new recipe or edit an existing one.
 */
class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    // ViewModel that gives access to the database through the repository
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if editing an existing recipe
        val isEditMode = intent.getBooleanExtra("EDIT_MODE", false)
        val existingId = intent.getIntExtra("RECIPE_ID", -1)
        Log.d("AddRecipeActivity", "Editing recipe with ID: $existingId")

        // Get recipe details passed through the intent
        val titleFromIntent = intent.getStringExtra("RECIPE_TITLE") ?: ""
        val ingredientsFromIntent = intent.getStringExtra("RECIPE_INGREDIENTS") ?: ""
        val instructionsFromIntent = intent.getStringExtra("RECIPE_INSTRUCTIONS") ?: ""
        val categoryFromIntent = intent.getStringExtra("RECIPE_CATEGORY") ?: "Others"

        if (isEditMode) {
            // Fill in the form with existing data
            binding.recipeNameInput.setText(titleFromIntent)
            binding.ingredientsInput.setText(ingredientsFromIntent)
            binding.instructionsInput.setText(instructionsFromIntent)
            binding.saveButton.text = getString(com.miroslava958.culinarycompanion.R.string.update)
        }

        // Set the selected item in the category spinner
        binding.categorySpinner.post {
            val adapter = binding.categorySpinner.adapter
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i)?.toString() == categoryFromIntent) {
                    binding.categorySpinner.setSelection(i)
                    break
                }
            }
        }

        // Cancel button
        binding.cancelButton.setOnClickListener { finish() }

        // Save button
        binding.saveButton.setOnClickListener {
            val title = binding.recipeNameInput.text.toString().trim()
            val formattedTitle = title.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
            val ingredients = binding.ingredientsInput.text.toString().trim()
            val instructions = binding.instructionsInput.text.toString().trim()
            val category = binding.categorySpinner.selectedItem.toString()

            // Make sure all fields are filled
            if (title.isBlank() || ingredients.isBlank() || instructions.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check for duplicate title
            lifecycleScope.launch {
                val isDuplicate = withContext(Dispatchers.IO) {
                    viewModel.isDuplicateRecipeTitle(title)
                }
                val isSameAsExisting =
                    isEditMode && viewModel.getRecipeById(existingId)?.title == title

                if (isDuplicate && !isSameAsExisting) {
                    Toast.makeText(
                        this@AddRecipeActivity,
                        "A recipe with this title already exists.",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@launch
                }

                // Create recipe object
                val recipe = Recipe(
                    id = if (isEditMode) existingId else 0,
                    title = formattedTitle,
                    category = category,
                    ingredients = ingredients,
                    instructions = instructions
                )

                // Save or update the recipe
                if (isEditMode) {
                    viewModel.updateRecipe(recipe)
                    Toast.makeText(this@AddRecipeActivity, "Recipe updated!", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.addRecipe(recipe)
                    Toast.makeText(this@AddRecipeActivity, "Recipe saved!", Toast.LENGTH_SHORT)
                        .show()
                }

                // Go to category screen after saving
                val intent = Intent(this@AddRecipeActivity, CategoriesTemplateActivity::class.java)
                intent.putExtra("CATEGORY_NAME", recipe.category)
                startActivity(intent)
                finish()
            }
        }
    }
}