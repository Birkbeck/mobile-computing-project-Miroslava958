package com.miroslava958.culinarycompanion

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.miroslava958.culinarycompanion.data.RecipeDao
import com.miroslava958.culinarycompanion.data.RecipeRepository
import com.miroslava958.culinarycompanion.model.Recipe
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class RecipeTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao: RecipeDao
    private lateinit var repository: RecipeRepository

    private val sampleRecipe = Recipe(
        id = 1,
        title = "Test Pizza",
        category = "Dinner",
        ingredients = "Cheese, Dough",
        instructions = "Bake it"
    )

    @Before
    fun setup() {
        dao = mock(RecipeDao::class.java)
        repository = RecipeRepository(dao)
    }

    @Test
    fun `insert recipe calls dao insert`() = runBlocking {
        repository.insert(sampleRecipe)
        verify(dao).insert(sampleRecipe)
    }

    @Test
    fun `update recipe calls dao update`() = runBlocking {
        repository.updateRecipe(sampleRecipe)
        verify(dao).updateRecipe(sampleRecipe)
    }

    @Test
    fun `delete recipe calls dao delete`() = runBlocking {
        repository.delete(sampleRecipe)
        verify(dao).delete(sampleRecipe)
    }

    @Test
    fun testIsDuplicateTitleReturnsTrueWhenCountGreaterThanZero() = runBlocking {
        `when`(dao.countByTitle("Test Pizza")).thenReturn(1)

        val result = repository.isDuplicateTitle("Test Pizza")
        assertTrue(result)
    }
}
