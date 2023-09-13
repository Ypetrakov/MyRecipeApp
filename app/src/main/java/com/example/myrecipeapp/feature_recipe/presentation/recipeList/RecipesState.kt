package com.example.myrecipeapp.feature_recipe.presentation.recipeList

import com.example.myrecipeapp.feature_recipe.domain.model.Recipe


data class RecipesState(
    val recipes: List<Recipe> = emptyList(),
    var isLoading: Boolean = false,
    var loadingType: LoadingType = LoadingType.LoadFromLocal
)