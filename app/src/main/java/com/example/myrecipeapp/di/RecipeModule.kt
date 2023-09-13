package com.example.myrecipeapp.di

import android.app.Application
import androidx.room.Room
import com.example.myrecipeapp.feature_recipe.data.local.Converters
import com.example.myrecipeapp.feature_recipe.data.local.RecipeDatabase
import com.example.myrecipeapp.feature_recipe.data.remote.NinjaRecipeApi
import com.example.myrecipeapp.feature_recipe.data.repository.RecipeRepositoryImpl
import com.example.myrecipeapp.feature_recipe.domain.repository.RecipeRepository
import com.example.myrecipeapp.feature_recipe.domain.use_case.DeleteRecipe
import com.example.myrecipeapp.feature_recipe.domain.use_case.GetGeneratedRecipes
import com.example.myrecipeapp.feature_recipe.domain.use_case.GetRecipe
import com.example.myrecipeapp.feature_recipe.domain.use_case.RecipeUseCase
import com.example.myrecipeapp.feature_recipe.domain.use_case.GetRecipes
import com.example.myrecipeapp.feature_recipe.domain.use_case.GetRecipesByTitle
import com.example.myrecipeapp.feature_recipe.domain.use_case.GetRecipesFromCategory
import com.example.myrecipeapp.feature_recipe.domain.use_case.InsertRecipe
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RecipeModule {

    @Provides
    @Singleton
    fun provideRecipeDataBase(app: Application): RecipeDatabase {
        return Room.databaseBuilder(
            app,
            RecipeDatabase::class.java,
            "recipe_db"
        ).addTypeConverter(Converters())
            .build()
    }

    @Provides
    @Singleton
    fun provideNinjaRecipeApi(): NinjaRecipeApi {
        return Retrofit.Builder()
            .baseUrl(NinjaRecipeApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NinjaRecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(
        db: RecipeDatabase,
        api: NinjaRecipeApi
    ): RecipeRepository {
        return RecipeRepositoryImpl(api, db)
    }

    @Provides
    @Singleton
    fun provideRecipeUseCases(repository: RecipeRepository): RecipeUseCase {
        return RecipeUseCase(
            getRecipe = GetRecipe(repository),
            getRecipes = GetRecipes(repository),
            getRecipesFromCategory = GetRecipesFromCategory(),
            getGeneratedRecipes = GetGeneratedRecipes(repository),
            insertRecipe = InsertRecipe(repository),
            getRecipesByTitle = GetRecipesByTitle(repository),
            deleteRecipe =  DeleteRecipe(repository)
        )
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()
}