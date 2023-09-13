package com.example.myrecipeapp.feature_recipe.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myrecipeapp.feature_recipe.data.local.entity.RecipeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipeentity")
    suspend fun getRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipeentity WHERE title = :title")
    suspend fun getRecipeByTitle(title: String): RecipeEntity?

    @Query("SELECT * FROM recipeentity WHERE category = :category")
    suspend fun getRecipeByCategory(category: String): List<RecipeEntity>

    @Query("DELETE FROM recipeentity WHERE title = :title")
    suspend fun deleteRecipe(title: String)

    @Query("Select * FROM recipeentity WHERE LOWER(title) LIKE '%' || :title || '%'")
    suspend fun getRecipesByTitle(title: String): List<RecipeEntity>
}