[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/8FwP1yxa)
# Culinary Companion

Culinary Companion is a Android application built with Kotlin using MVVM architecture. It allows users to manage their favorite recipes by adding, viewing, editing, and deleting them. Recipes can also be displayed by category or search for.

## Features

- Add new recipes (title, ingredients, instructions)
- View and scroll through a list of recipes
- Search for recipes
- Delete or update existing recipes
- Uses Room database for local storage

## Built With

- **Kotlin**
- **Room**
- **ViewModel and LiveData**
- **Data Binding**
- **JUnit**
- **Mockito**

## Project Structure

CulinaryCompanion/
1. data/
1.1. AppDatabase.kt
1.2. RecipeDao.kt
1.3. RecipeRepository.kt
2. model/
2.1. Recipe.kt
3. ui/
3.1. CategoriesTemplateActiviry.kt
3.2. RecipeActivity
3.3. ViewCategooriesActivity
3.4. AddRecipeActivity.kt
4. viewmodel/
4.1. RecipeViewModel.kt
5. res/
5.1. layout/
5.2. values/
5.3. drawable/
6. test/
6.1. RecipeTest.kt

## Testing

### Unit testing is implemented using JUnit and Mockito to verify the logic of the application about inserting recipes, updating recipes, deleting recipes and checking for duplicate recipe titles


## Author

**Miroslava**  
Built as part of a uni project.


## License
 
Free to use, modify, and share.
