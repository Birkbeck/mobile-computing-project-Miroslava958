package com.miroslava958.culinarycompanion.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miroslava958.culinarycompanion.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM recipes ORDER BY id DESC")
    fun getAll(): LiveData<List<Recipe>>

    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY id DESC")
    fun getByCategory(category: String): LiveData<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(recipe: Recipe)
}