package com.miroslava958.culinarycompanion.viewmodel

import androidx.lifecycle.*
import com.miroslava958.culinarycompanion.data.RecipeRepository
import com.miroslava958.culinarycompanion.model.Recipe
import kotlinx.coroutines.launch

class RecipeViewModel(private val repository: RecipeRepository) : ViewModel() {

    val allRecipes: LiveData<List<Recipe>> = repository.getAll()

    fun recipesByCategory(cat: String): LiveData<List<Recipe>> =
        repository.getByCategory(cat)

    fun addRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    /* ---------- Factory ---------- */
    companion object {
        fun provideFactory(repository: RecipeRepository): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(repository) as T
                }
            }
    }
}