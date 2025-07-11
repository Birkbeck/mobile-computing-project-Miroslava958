package com.miroslava958.culinarycompanion.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.R

class ViewCategoriesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_categories)

        val btnBreakfast = findViewById<Button>(R.id.btnBreakfast)
        val btnLunch = findViewById<Button>(R.id.btnLunch)
        val btnDessert = findViewById<Button>(R.id.btnDessert)
        val btnDinner = findViewById<Button>(R.id.btnDinner)
        val btnBrunch = findViewById<Button>(R.id.btnBrunch)
        val btnOthers = findViewById<Button>(R.id.btnOthers)

        btnBreakfast.setOnClickListener { openCategory("Breakfast") }
        btnLunch.setOnClickListener { openCategory("Lunch") }
        btnDessert.setOnClickListener { openCategory("Dessert") }
        btnDinner.setOnClickListener { openCategory("Dinner") }
        btnBrunch.setOnClickListener { openCategory("Brunch") }
        btnOthers.setOnClickListener { openCategory("Others") }
    }

    private fun openCategory(category: String) {
        Log.d("ViewCategories", "Opening category: $category")
        val intent = Intent(this, CategoriesTemplateActivity::class.java)
        intent.putExtra("CATEGORY_NAME", category)
        startActivity(intent)
    }
}