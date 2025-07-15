package com.miroslava958.culinarycompanion.data

import com.miroslava958.culinarycompanion.data.RecipeDao
import com.miroslava958.culinarycompanion.model.Recipe

class RecipeRepository(private val dao: RecipeDao) {

    fun getAll()              = dao.getAll()
    fun getByCategory(cat: String) = dao.getByCategory(cat)

    suspend fun insert(recipe: Recipe) = dao.insert(recipe)
}