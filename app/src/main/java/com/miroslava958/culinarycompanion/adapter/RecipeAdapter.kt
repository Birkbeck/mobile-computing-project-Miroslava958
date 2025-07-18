package com.miroslava958.culinarycompanion.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miroslava958.culinarycompanion.R
import com.miroslava958.culinarycompanion.databinding.ItemRecipeBinding
import com.miroslava958.culinarycompanion.model.Recipe

/**
 * Adapter for displaying a list of Recipe items in a RecyclerView using data binding.
 *
 * @property recipes The list of recipes to display.
 * @property onItemClick Callback triggered when a recipe is clicked.
 */

class RecipeAdapter(
    private var recipes: List<Recipe>,
    private val onItemClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    init {
        Log.d("Adapter", "Adapter initialized with ${recipes.size} items")
    }

    /**
     * ViewHolder binds the [Recipe] data to the layout using data binding.
     *
     * @param binding The generated binding object for the item layout.
     */
    inner class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Binds a single [Recipe] to the item layout.
         *
         * @param recipe The recipe to bind to the view.
         */
        fun bind(recipe: Recipe) {
            Log.d("RecipeAdapter", "Binding recipe: ${recipe.title}")
            binding.recipe = recipe  // Binds recipe data to the layout via XML
            binding.executePendingBindings()

            // Handle item click
            binding.root.setOnClickListener {
                Log.d("RecipeAdapter", "Recipe clicked: ${recipe.title}")
                onItemClick(recipe)
            }
        }
    }

    /**
     * Called when RecyclerView needs a new ViewHolder of the given type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        Log.d("Adapter-inflate", "layout id = ${R.layout.item_recipe}")

        // Inflate layout using view binding
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding)
    }

    /**
     * Binds the data to the ViewHolder at the given position.
     */
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    /**
     * Returns the total number of items in the dataset.
     */
    override fun getItemCount(): Int = recipes.size

    /**
     * Replaces the current list of recipes and refreshes the view.
     *
     * @param newList The new list of recipes to display.
     */
    fun updateRecipes(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}