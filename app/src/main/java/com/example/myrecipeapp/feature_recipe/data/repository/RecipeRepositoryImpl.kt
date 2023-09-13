package com.example.myrecipeapp.feature_recipe.data.repository

import androidx.room.Database
import com.example.myrecipeapp.feature_recipe.data.local.RecipeDao
import com.example.myrecipeapp.feature_recipe.data.local.RecipeDatabase
import com.example.myrecipeapp.feature_recipe.data.mapper.toRecipe
import com.example.myrecipeapp.feature_recipe.data.mapper.toRecipeEntity
import com.example.myrecipeapp.feature_recipe.data.remote.NinjaRecipeApi
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository
import com.example.myrecipeapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RecipeRepositoryImpl (
    private val api: NinjaRecipeApi,
    private val db: RecipeDatabase
): RecipeRepository {

    private val dao = db.recipeDao

    override suspend fun getRecipeByTitle(title: String): Resource<Recipe> {

        val recipe = dao.getRecipeByTitle(title)?.toRecipe()
            ?: return Resource.Error(message = "No '$title' in base")
        return Resource.Success(recipe)

    }

    override fun getRecipesByCategory(category: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading(true))
        val recipes = dao.getRecipeByCategory(category).map { it.toRecipe() }
        if (recipes.isEmpty()) {
            emit(Resource.Error(message = "No recipes in $category category"))
        }
        emit(Resource.Success(data = recipes))
    }

    override fun getGeneratedRecipes(name: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading(true))
        try {
            val recipes = api.getRecipe(title = name)
            emit(Resource.Success(data = recipes.map { it.toRecipe() }))

        } catch (e: HttpException) {
            emit(Resource.Error(message = "Something go wrong!"))
        } catch (e: IOException){
            emit(Resource.Error(message = "Couldn't reach Server, check your internet connection!"))
        }
    }

    override suspend fun insertRecipe(recipe: Recipe) {
        dao.insertRecipe(recipe.toRecipeEntity())
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        dao.deleteRecipe(recipe.title)
    }

    override fun getRecipes(): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading(true))
        val recipes = dao.getRecipes().map { it.toRecipe() }
        emit(Resource.Success(data = recipes))
    }

    override fun getRecipesByTitle(title: String): Flow<Resource<List<Recipe>>> = flow {
        emit(Resource.Loading(true))
        val recipes = dao.getRecipesByTitle(title).map {it.toRecipe()}
        emit(Resource.Success(data = recipes))
    }
}