package com.miroslava958.culinarycompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miroslava958.culinarycompanion.model.Recipe

/**
 * The Room database for storing and accessing Recipe data.
 *
 * This class defines the database schema and provides a
 * instance so only one database is used throughout the app.
 */
@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Provides access to the DAO for performing database operations on recipes.
     */
    abstract fun recipeDao(): RecipeDao

    companion object {
        // Volatile provides visibility across threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Returns a instance of the AppDatabase.
         * If the instance does not exist, it builds one using Room.
         *
         * @param context The application context used to create the database.
         * @return A instance of AppDatabase.
         */
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                // Double-check for thread-safe singleton
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "recipe_database"
                ).build().also { INSTANCE = it }
            }
    }
}