package com.example.myrecipeapp.feature_recipe.domain.use_case

import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository

class InsertRecipe(val repository: RecipeRepository) {
    suspend operator fun invoke(recipe: Recipe) {
        repository.insertRecipe(recipe)
    }
}