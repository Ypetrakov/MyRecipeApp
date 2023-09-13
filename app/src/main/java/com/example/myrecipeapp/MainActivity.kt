package com.example.myrecipeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.presentation.addEditRecipe.AddEditRecipeScreen
import com.example.myrecipeapp.feature_recipe.presentation.recipeList.RecipeListScreen
import com.example.myrecipeapp.feature_recipe.presentation.util.Screen
import com.example.myrecipeapp.ui.theme.MyRecipeAppTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var gson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyRecipeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.RecipesScreen.route
                    ) {
                        composable(
                            route = Screen.RecipesScreen.route
                        ) {
                            RecipeListScreen(navController)
                        }
                        composable(
                            route = "${Screen.AddEditRecipeScreen.route}?recipe={recipe}",
                            arguments = listOf(
                                navArgument("recipe") {
                                    type = NavType.StringType
                                    defaultValue = gson.toJson(Recipe())
                                }
                            )
                        ) { backStackEntry ->
                            val recipeJson = backStackEntry.arguments?.getString("recipe")
                            val recipe = gson.fromJson(recipeJson, Recipe::class.java) ?: Recipe()
                            AddEditRecipeScreen(
                                recipe = recipe,
                                navController = navController
                            )
                        }
                    }

                }
            }
        }
    }
}
