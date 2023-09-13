package com.example.myrecipeapp.feature_recipe.presentation.addEditRecipe

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.use_case.RecipeUseCase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditRecipeViewModel @Inject constructor(
    val gson: Gson,
    val useCase: RecipeUseCase
    ) : ViewModel() {

    var recipeState by mutableStateOf(Recipe())
        private set


    fun setRecipe(recipe: Recipe) {
        recipeState = recipe
    }

    fun changeTitle(title: String) {
        recipeState = recipeState.copy(title = title)
        Log.d("newtitle", title)

    }

    fun changeCategory(category: String) {
        recipeState = recipeState.copy(category = category)
    }

    fun changeServings(servings: String) {
        recipeState = recipeState.copy(servings = servings)
    }

    fun changeIngredients(ingredients: List<String>) {
        recipeState = recipeState.copy(
            ingredients = ingredients
        )
    }

    fun changeInstructions(instructions: List<String>) {
        recipeState = recipeState.copy(
            instructions = instructions
        )
    }


    fun insertRecipe(ingredients: List<String>, instructions: List<String>){
        CoroutineScope(Dispatchers.IO).launch {
            useCase.insertRecipe(recipeState.copy(
                instructions = instructions,
                ingredients = ingredients
            ))
        }
    }

}