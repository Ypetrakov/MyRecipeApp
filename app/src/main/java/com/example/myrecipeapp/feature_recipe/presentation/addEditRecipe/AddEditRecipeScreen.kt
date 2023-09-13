package com.example.myrecipeapp.feature_recipe.presentation.addEditRecipe

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.feature_recipe.presentation.addEditRecipe.components.StringList
import com.example.myrecipeapp.feature_recipe.presentation.recipeList.LoadingType
import com.example.myrecipeapp.feature_recipe.presentation.util.Screen
import com.example.myrecipeapp.ui.theme.MyRecipeAppTheme


@Composable
fun AddEditRecipeScreen(
    navController: NavController,
    recipe: Recipe,
    modifier: Modifier = Modifier,
    viewModel: AddEditRecipeViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.setRecipe(recipe)
    }

    val ingredients = remember {
        recipe.ingredients.toMutableStateList()
    }

    val instruction = remember {
        recipe.instructions.toMutableStateList()
    }


    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            OutlinedButton(
                contentPadding = PaddingValues(0.dp),
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.size(64.dp),
                onClick = {
                    viewModel.insertRecipe(ingredients = ingredients, instructions = instruction)

                    navController.navigate(Screen.RecipesScreen.route) {
                        popUpTo(Screen.RecipesScreen.route) {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(
                    imageVector =  Icons.Default.Save,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
                .padding(top = padding.calculateTopPadding(), bottom = 74.dp)
        ) {
            val titleError = viewModel.recipeState.title.isEmpty() or viewModel.recipeState.title.isBlank()
            val servingsError = viewModel.recipeState.servings.toIntOrNull() == null || viewModel.recipeState.servings == "0"

            TextField(
                value = viewModel.recipeState.title,
                colors = TextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer),
                textStyle = MaterialTheme.typography.titleLarge,
                isError = titleError,
                label = { Text("Title") },
                onValueChange = { updatedTitle -> viewModel.changeTitle(updatedTitle) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                trailingIcon = {
                    if (titleError) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        tint = Color.Red,
                        contentDescription = null,
                    )
                }}
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = viewModel.recipeState.servings,
                colors = TextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer),
                textStyle = MaterialTheme.typography.titleLarge,
                label = { Text("Servings") },
                onValueChange = { updatedServing -> viewModel.changeServings(updatedServing) },
                modifier = Modifier.fillMaxWidth(),
                isError = servingsError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    if (servingsError) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            tint = Color.Red,
                            contentDescription = null,
                        )
                    }}
            )
            Spacer(modifier = Modifier.height(10.dp))
            StringList(
                stringList = ingredients,
                label = "Ingredient",
                onAddButton = { ingredients.add("") },
                modifier = Modifier.weight(1f),
                onListChange = { index: Int, string: String -> ingredients[index] = string },
                onDelete = { index -> ingredients.removeAt(index)}
            )
            Spacer(modifier = Modifier.height(10.dp))
            StringList(
                stringList = instruction,
                label = "Instruction",
                modifier = Modifier.weight(1f),
                onAddButton = { instruction.add("") },
                onListChange = { index: Int, string: String -> instruction[index] = string },
                onDelete = { index -> instruction.removeAt(index) }
            )
        }
    }

}
