package com.example.myrecipeapp.feature_recipe.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.myrecipeapp.feature_recipe.data.local.entity.RecipeEntity
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import dagger.Provides

    @ProvidedTypeConverter
    class Converters {
        @TypeConverter
        fun fromRecipesString(str: String): List<String> {
            return str.split("//")
        }

        @TypeConverter
        fun toRecipesString(recipes: List<String>): String {
            return recipes.joinToString("//")
        }
    }