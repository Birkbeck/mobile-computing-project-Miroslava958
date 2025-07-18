package com.miroslava958.culinarycompanion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.miroslava958.culinarycompanion.data.AppDatabase
import com.miroslava958.culinarycompanion.data.RecipeRepository
import com.miroslava958.culinarycompanion.model.Recipe
import kotlinx.coroutines.launch

/**
 * ViewModel connects the UI with the data.
 * It handles all logic related to recipes using the Repository.
 */

class RecipeViewModel private constructor( // Private constructor used by factory
    application: Application,
) : AndroidViewModel(application) {

    // Set up the repository, uses DAO from Room database
    private val repository: RecipeRepository by lazy {
        val dao = AppDatabase.getInstance(application).recipeDao()
        RecipeRepository(dao)
    }

    // LiveData for all recipes
    val allRecipes: LiveData<List<Recipe>> = repository.getAll()

    // Get recipes filtered by category
    fun recipesByCategory(cat: String): LiveData<List<Recipe>> =
        repository.getByCategory(cat)

    // Add a recipe to the database
    fun addRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    // Delete a recipe from the database
    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.delete(recipe)
    }

    // Update a recipe in the database
    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.updateRecipe(recipe)
        }
    }

    // Check if a recipe with the same title already exists
    suspend fun isDuplicateRecipeTitle(title: String): Boolean {
        return repository.isDuplicateTitle(title)
    }

    // Get a recipe by its ID
    suspend fun getRecipeById(id: Int): Recipe? {
        return repository.getRecipeById(id)
    }

    companion object {
        /**
         * Factory to create the ViewModel with access to the Application context.
         * Used with: `by viewModels { provideFactory(application) }`
         */

        fun provideFactory(app: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(app) as T
                }
            }
    }
}