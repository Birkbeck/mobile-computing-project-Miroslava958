package com.miroslava958.culinarycompanion.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class represents a recipe in the app.
 * It is stored in the database using Room.
 */
@Entity(tableName = "recipes")
data class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Unique ID for each recipe (auto-incremented)
    val title: String,         // Name of the recipe
    val category: String,      // Recipe category
    val ingredients: String,   // Ingredients list
    val instructions: String   // Cooking instructions
)