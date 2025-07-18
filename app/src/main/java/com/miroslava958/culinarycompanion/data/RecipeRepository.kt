package com.miroslava958.culinarycompanion.data

import com.miroslava958.culinarycompanion.model.Recipe

/**
 * Repository class that acts as an abstraction layer between the ViewModel and the DAO.
 *
 * It connects the app to the database and keeps the code organized,
 * so the ViewModel does not work with the DAO directly.
 *
 * @property dao The Data Access Object used to interact with the Room database.
 */
class RecipeRepository(private val dao: RecipeDao) {

    /**
     * Returns a LiveData list of all recipes in the database.
     */
    fun getAll() = dao.getAll()

    /**
     * Returns a LiveData list of recipes filtered by the given category.
     *
     * @param cat The category to filter recipes by.
     */
    fun getByCategory(cat: String) = dao.getByCategory(cat)

    /**
     * Inserts a new recipe into the database.
     * If a recipe with the same ID exists, it will be replaced.
     *
     * @param recipe The recipe to insert.
     */
    suspend fun insert(recipe: Recipe) = dao.insert(recipe)

    /**
     * Updates an existing recipe in the database.
     *
     * @param recipe The recipe with updated values.
     */
    suspend fun updateRecipe(recipe: Recipe) {
        dao.updateRecipe(recipe)
    }

    /**
     * Deletes a recipe from the database.
     *
     * @param recipe The recipe to delete.
     */
    suspend fun delete(recipe: Recipe) {
        dao.delete(recipe)
    }

    /**
     * Checks if a recipe with the same title already exists case-insensitive.
     *
     * @param title The title to check for duplication.
     * @return True if a duplicate exists, false otherwise.
     */
    suspend fun isDuplicateTitle(title: String): Boolean {
        return dao.countByTitle(title) > 0
    }

    /**
     * Retrieves a single recipe by its ID.
     *
     * @param id The ID of the recipe.
     * @return The matching [Recipe], or null if not found.
     */
    suspend fun getRecipeById(id: Int): Recipe? {
        return dao.getById(id)
    }
}