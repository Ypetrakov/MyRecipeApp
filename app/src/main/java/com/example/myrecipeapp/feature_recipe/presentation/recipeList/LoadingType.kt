package com.example.myrecipeapp.feature_recipe.presentation.recipeList

sealed interface LoadingType{
    object LoadFromInternet: LoadingType
    object LoadFromLocal: LoadingType

    companion object {
        fun changeType(loadingType: LoadingType): LoadingType {
            return when (loadingType) {
                LoadFromLocal -> LoadFromInternet
                LoadFromInternet -> LoadFromLocal
            }
        }
    }
}
