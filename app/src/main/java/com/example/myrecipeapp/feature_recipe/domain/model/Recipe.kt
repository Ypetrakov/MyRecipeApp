package com.example.myrecipeapp.feature_recipe.domain.model

import com.google.gson.annotations.SerializedName

data class Recipe(
    val ingredients: List<String> = mutableListOf(""),
    val instructions: List<String> = mutableListOf(""),
    val servings: String = "0",
    val title: String = "",
    val category: String = "Unknown"
)