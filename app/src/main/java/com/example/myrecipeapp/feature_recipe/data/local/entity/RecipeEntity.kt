package com.example.myrecipeapp.feature_recipe.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe

@Entity(
    indices = [Index(value = ["title"], unique = true)]
)
data class RecipeEntity(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val servings: String,
    val ingredients: List<String>,
    val instructions: List<String>,
    val category: String = "Unknown"
)


