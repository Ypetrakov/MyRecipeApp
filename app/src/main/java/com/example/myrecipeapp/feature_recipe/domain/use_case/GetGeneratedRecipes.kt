package com.example.myrecipeapp.feature_recipe.domain.use_case

import com.example.myrecipeapp.feature_recipe.data.remote.NinjaRecipeApi
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository
import com.example.myrecipeapp.util.Resource
import kotlinx.coroutines.flow.Flow

class GetGeneratedRecipes(
    private val repository: RecipeRepository
) {
    operator fun invoke(title: String) : Flow<Resource<List<Recipe>>> {
        return repository.getGeneratedRecipes(title)
    }
}