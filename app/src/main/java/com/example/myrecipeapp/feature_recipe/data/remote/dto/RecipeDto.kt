package com.example.myrecipeapp.feature_recipe.data.remote.dto


import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.google.gson.annotations.SerializedName

data class RecipeDto(
    @SerializedName("ingredients")
    val ingredients: String,
    @SerializedName("instructions")
    val instructions: String,
    @SerializedName("servings")
    val servings: String,
    @SerializedName("title")
    val title: String
)

