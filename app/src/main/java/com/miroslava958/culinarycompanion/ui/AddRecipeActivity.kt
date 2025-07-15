package com.miroslava958.culinarycompanion.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.databinding.ActivityAddRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe
import com.miroslava958.culinarycompanion.viewmodel.RecipeViewModel

class AddRecipeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRecipeBinding

    /* ViewModel obtained via the single-argument factory */
    private val viewModel: RecipeViewModel by viewModels {
        RecipeViewModel.provideFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityAddRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // UI actions

        // Cancel (X) button
        binding.cancelButton.setOnClickListener { finish() }

        // Save button
        binding.saveButton.setOnClickListener {
            val title        = binding.recipeNameInput.text.toString().trim()
            val ingredients  = binding.ingredientsInput.text.toString().trim()
            val instructions = binding.instructionsInput.text.toString().trim()
            val category     = binding.categorySpinner.selectedItem.toString()

            if (title.isBlank() || ingredients.isBlank() || instructions.isBlank()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val recipe = Recipe(
                title = title,
                category = category,
                ingredients = ingredients,
                instructions = instructions
            )

            viewModel.addRecipe(recipe)                       // stored in Room
            Toast.makeText(this, "Recipe saved!", Toast.LENGTH_SHORT).show()
            finish()                                          // back to previous screen
        }
    }
}
