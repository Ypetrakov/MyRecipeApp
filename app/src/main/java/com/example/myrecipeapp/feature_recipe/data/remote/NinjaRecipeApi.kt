package com.example.myrecipeapp.feature_recipe.data.remote

import com.example.myrecipeapp.feature_recipe.data.remote.dto.RecipeDto
import com.example.myrecipeapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NinjaRecipeApi {
    @GET("recipe")
    suspend fun getRecipe(
        @Header("X-Api-Key") apiKey: String = API_KEY,
        @Query("query") title: String
    ): List<RecipeDto>

    companion object {
        val API_KEY = "GY9QIcw4Dj6Ob7wEJF1xxw==SH9IRlH80ZJHMgpJ"
        const val BASE_URL = "https://api.api-ninjas.com/v1/"
    }
}