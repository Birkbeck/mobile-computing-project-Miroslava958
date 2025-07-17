package com.miroslava958.culinarycompanion.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.miroslava958.culinarycompanion.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY id DESC")
    fun getByCategory(category: String): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT COUNT(*) FROM recipes WHERE LOWER(title) = LOWER(:title)")
    suspend fun countByTitle(title: String): Int

    @Query("SELECT * FROM recipes WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): Recipe?
}
