package com.example.myrecipeapp.feature_recipe.domain.repository

import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    suspend fun getRecipeByTitle(title: String): Resource<Recipe>

    fun getGeneratedRecipes(name: String): Flow<Resource<List<Recipe>>>

    fun getRecipesByCategory(category: String): Flow<Resource<List<Recipe>>>

    suspend fun insertRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getRecipes(): Flow<Resource<List<Recipe>>>

    fun getRecipesByTitle(title: String): Flow<Resource<List<Recipe>>>

}