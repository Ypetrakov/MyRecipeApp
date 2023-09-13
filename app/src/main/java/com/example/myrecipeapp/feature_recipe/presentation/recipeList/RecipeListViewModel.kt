package com.example.myrecipeapp.feature_recipe.presentation.recipeList

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.use_case.RecipeUseCase
import com.example.myrecipeapp.util.Resource
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val recipeUseCase: RecipeUseCase,
    val gson: Gson
) : ViewModel() {

    private var getRecipesJob: Job? = null


    var savedRecipe: Recipe = Recipe()
        set(value) {
            field = if (field == value) Recipe() else value
        }

    private val _state = mutableStateOf(RecipesState())
    val state: State<RecipesState> = _state

    private val _searchState = mutableStateOf("")
    val searchState: State<String> = _searchState


    private fun getRecipesFromInternet(title: String) {
        if (title.isBlank() or title.isEmpty()) {
            return
        }
        getRecipesJob?.cancel()
        getRecipesJob = recipeUseCase.getGeneratedRecipes(title).onEach { recipes ->
            when (recipes) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        recipes = recipes.data ?: emptyList(),
                        isLoading = false,
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        recipes = emptyList(),
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        recipes = emptyList(),
                        isLoading = true,
                    )
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun getRecipesFromLocal(title: String) {
        getRecipesJob?.cancel()
        getRecipesJob = recipeUseCase.getRecipesByTitle(title).onEach { recipes ->
            when (recipes) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        recipes = recipes.data ?: emptyList(),
                        isLoading = false,
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        recipes = emptyList(),
                        isLoading = false,
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        recipes = emptyList(),
                        isLoading = true,
                    )
                }
            }

        }.launchIn(CoroutineScope(Dispatchers.IO))
    }

    fun onChange(query: String) {
        _searchState.value = query
        when (_state.value.loadingType) {
            LoadingType.LoadFromInternet -> onChangeWithInternet(query)
            LoadingType.LoadFromLocal -> getRecipesFromLocal(query)
        }
    }

    private fun onChangeWithInternet(query: String) {
        if (query.isBlank() or query.isEmpty()) {
            _state.value = _state.value.copy(
                recipes = emptyList(),
                isLoading = false,
            )
            getRecipesJob?.cancel()
            return
        }
        getRecipesFromInternet(title = _searchState.value.trim())
    }

    fun deleteRecipe(recipe: Recipe){
        viewModelScope.launch {
            recipeUseCase.deleteRecipe(recipe)
            getRecipesFromLocal(_searchState.value)
        }
    }

    fun changeMod() {
        _state.value = _state.value.copy(
            loadingType = LoadingType.changeType(_state.value.loadingType)
        )
        when (_state.value.loadingType) {
            LoadingType.LoadFromLocal -> getRecipesFromLocal(_searchState.value )
            LoadingType.LoadFromInternet -> onChangeWithInternet(_searchState.value.trim())
        }
    }
}