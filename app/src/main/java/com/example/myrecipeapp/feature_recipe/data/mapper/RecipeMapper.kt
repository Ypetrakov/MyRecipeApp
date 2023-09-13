package com.example.myrecipeapp.feature_recipe.data.mapper

import com.example.myrecipeapp.feature_recipe.data.local.entity.RecipeEntity
import com.example.myrecipeapp.feature_recipe.data.remote.dto.RecipeDto
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe

fun RecipeDto.toRecipe(): Recipe =
    Recipe(
        ingredients = ingredients.split("|"),
        title = title,
        servings = servings.split(" ")[0],
        instructions = instructions.split(". "),
        category = "Unknown"
    )

fun Recipe.toRecipeEntity(): RecipeEntity =
    RecipeEntity(
        ingredients = ingredients,
        title = title,
        servings = servings,
        instructions = instructions,
        category = category
    )


fun RecipeEntity.toRecipe(): Recipe =
    Recipe(
        ingredients = ingredients,
        title = title,
        servings = servings,
        instructions = instructions,
        category = category
    )