package com.example.myrecipeapp.feature_recipe.domain.use_case

import androidx.compose.ui.text.toLowerCase
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository
import com.example.myrecipeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.Locale

class GetRecipesByTitle(
    private val repository: RecipeRepository
) {
    operator fun invoke(title: String): Flow<Resource<List<Recipe>>> {
        return repository.getRecipesByTitle(title.lowercase(Locale.getDefault()))
    }
}