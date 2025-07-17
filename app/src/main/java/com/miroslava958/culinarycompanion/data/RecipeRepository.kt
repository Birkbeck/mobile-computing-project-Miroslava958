package com.miroslava958.culinarycompanion.data

import com.miroslava958.culinarycompanion.model.Recipe

class RecipeRepository(private val dao: RecipeDao) {

    fun getAll()              = dao.getAll()
    fun getByCategory(cat: String) = dao.getByCategory(cat)

    suspend fun insert(recipe: Recipe) = dao.insert(recipe)

    suspend fun updateRecipe(recipe: Recipe) {
        dao.updateRecipe(recipe)
    }

    suspend fun delete(recipe: Recipe) {
        dao.delete(recipe)
    }

    suspend fun isDuplicateTitle(title: String): Boolean {
        return dao.countByTitle(title) > 0
    }

    suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getById(id)
    }

}