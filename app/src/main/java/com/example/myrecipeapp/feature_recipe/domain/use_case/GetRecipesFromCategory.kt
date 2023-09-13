package com.example.myrecipeapp.feature_recipe.domain.use_case

import com.example.myrecipeapp.feature_recipe.domain.model.Recipe

class GetRecipesFromCategory(
) {
    operator fun invoke(recipes: List<Recipe>, category: String): List<Recipe> {
        val recipesInCategory = mutableListOf<Recipe>()
        recipes.forEach { recipe: Recipe ->
            if (recipe.category == category) {
                recipesInCategory.add(recipe)
            }
        }
        return recipesInCategory
    }
}