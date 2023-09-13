package com.example.myrecipeapp.feature_recipe.domain.use_case

import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository
import com.example.myrecipeapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetRecipes(
    private val repository: RecipeRepository
) {
    operator fun invoke(): Flow<Resource<List<Recipe>>> {
        return repository.getRecipes()
    }
}