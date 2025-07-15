package com.miroslava958.culinarycompanion

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miroslava958.culinarycompanion.databinding.ActivityMainBinding
import com.miroslava958.culinarycompanion.ui.AddRecipeActivity
import com.miroslava958.culinarycompanion.ui.ViewCategoriesActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ADD button point to AddRecipeActivity
        binding.btnMainAdd.setOnClickListener {
            startActivity(Intent(this, AddRecipeActivity::class.java))
        }

        // VIEW button point to ViewCategoriesActivity
        binding.btnMainView.setOnClickListener {
            startActivity(Intent(this, ViewCategoriesActivity::class.java))
        }
    }
}
