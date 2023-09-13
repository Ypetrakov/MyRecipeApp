package com.example.myrecipeapp.feature_recipe.domain.use_case

import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository
import com.example.myrecipeapp.util.Resource

class GetRecipe(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(title: String): Resource<Recipe> {
        return repository.getRecipeByTitle(title)
    }
}