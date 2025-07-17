package com.miroslava958.culinarycompanion.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.miroslava958.culinarycompanion.data.AppDatabase
import com.miroslava958.culinarycompanion.data.RecipeRepository
import com.miroslava958.culinarycompanion.model.Recipe
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RecipeViewModel private constructor(            // ← private ctor
    application: Application,
) : AndroidViewModel(application) {

    /* ---------- data layer ---------- */
    private val repository: RecipeRepository by lazy {
        val dao = AppDatabase.getInstance(application).recipeDao()
        RecipeRepository(dao)
    }

    /* ---------- streams exposed to UI ---------- */
    val allRecipes: LiveData<List<Recipe>> = repository.getAll()
    fun recipesByCategory(cat: String): LiveData<List<Recipe>> =
        repository.getByCategory(cat)

    /* ---------- public actions ---------- */
    fun addRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.insert(recipe)
    }

    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch {
        repository.delete(recipe)
    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch {
            repository.updateRecipe(recipe)
        }
    }

    fun isDuplicateRecipeTitle(title: String): Boolean {
        return runBlocking {
            repository.isDuplicateTitle(title)
        }
    }

    fun getRecipeById(id: Int): Recipe? {
        return runBlocking {
            repository.getRecipeById(id)
        }
    }


    /* ---------- factory ---------- */
    companion object {

        /** Returns a factory that any Activity/Fragment can pass to `by viewModels { … }` */
        fun provideFactory(app: Application): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return RecipeViewModel(app) as T
                }
            }
    }
}