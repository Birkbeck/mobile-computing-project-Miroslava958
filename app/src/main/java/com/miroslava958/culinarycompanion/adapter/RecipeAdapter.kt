package com.miroslava958.culinarycompanion.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miroslava958.culinarycompanion.R
import com.miroslava958.culinarycompanion.databinding.ItemRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe

/**
 * Adapter for displaying a list of Recipe items using Data Binding.
 */
class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    init {
        Log.d("Adapter", "Adapter initialized with ${recipes.size} items")  // 🔍 Confirm adapter is constructed
    }
    /**
     * ViewHolder class that binds the Recipe data to the item layout.
     */
    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: Recipe) {
            Log.d("RecipeAdapter", "Binding recipe: ${recipe.title}")
            binding.recipe = recipe  // Binds to @{recipe.title}.
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onItemClick(recipe)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        Log.d("Adapter-inflate", "layout id = ${R.layout.item_recipe}")

        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    /**
     * Updates the list of recipes and refreshes the RecyclerView.
     */
    fun updateRecipes(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}