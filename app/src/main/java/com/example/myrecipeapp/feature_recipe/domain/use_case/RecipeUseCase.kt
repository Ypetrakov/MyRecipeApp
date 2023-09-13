package com.example.myrecipeapp.feature_recipe.domain.use_case

data class RecipeUseCase(
    val getRecipe: GetRecipe,
    val getRecipes: GetRecipes,
    val getRecipesFromCategory: GetRecipesFromCategory,
    val getRecipesByTitle: GetRecipesByTitle,
    val getGeneratedRecipes: GetGeneratedRecipes,
    val insertRecipe: InsertRecipe,
    val deleteRecipe: DeleteRecipe
)
