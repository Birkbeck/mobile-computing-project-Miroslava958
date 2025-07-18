package com.miroslava958.culinarycompanion.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miroslava958.culinarycompanion.model.Recipe

/**
 * Data Access Object for performing CRUD operations on the Recipe
 *
 * The interface defines all SQL queries and links them to suspend functions
 * or LiveData return types for Room to auto-generate implementation.
 */
@Dao
interface RecipeDao {

    /**
     * Retrieves all recipes from the database in descending order by ID.
     *
     * @return A LiveData list of all recipes.
     */
    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<Recipe>>

    /**
     * Retrieves all recipes matching the given category.
     *
     * @param category The category to filter by.
     * @return A LiveData list of recipes in the chosen category.
     */
    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY id DESC")
    fun getByCategory(category: String): LiveData<List<Recipe>>

    /**
     * Inserts a recipe into the database.
     * If a conflict occurs like same primary key, it replaces the existing recipe.
     *
     * @param recipe The recipe to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    /**
     * Deletes a recipe from the database.
     *
     * @param recipe The recipe to delete.
     */
    @Delete
    suspend fun delete(recipe: Recipe)

    /**
     * Updates an existing recipe in the database.
     *
     * @param recipe The recipe with updated fields.
     */
    @Update
    suspend fun updateRecipe(recipe: Recipe)

    /**
     * Counts how many recipes exist with the given title case-insensitive.
     *
     * @param title The title to check for duplicates.
     * @return The number of matching titles.
     */
    @Query("SELECT COUNT(*) FROM recipes WHERE LOWER(title) = LOWER(:title)")
    suspend fun countByTitle(title: String): Int

    /**
     * Retrieves a single recipe by its ID.
     *
     * @param id The ID of the recipe.
     * @return The matching Recipe, or null if not found.
     */
    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Recipe?
}