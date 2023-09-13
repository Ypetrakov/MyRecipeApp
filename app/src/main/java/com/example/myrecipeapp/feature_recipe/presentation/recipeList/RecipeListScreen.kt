package com.example.myrecipeapp.feature_recipe.presentation.recipeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalPizza
import androidx.compose.material.icons.filled.NetworkWifi
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myrecipeapp.feature_recipe.presentation.recipeList.components.Recipe
import com.example.myrecipeapp.feature_recipe.presentation.recipeList.components.SearchBar
import com.example.myrecipeapp.feature_recipe.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun RecipeListScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: RecipeListViewModel = hiltViewModel(),
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true ) {
        viewModel.onChange("")
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            SearchBar(
                value = viewModel.searchState.value,
                onValueChange = viewModel::onChange,
                onAddClicked = {
                    navController.navigate(Screen.AddEditRecipeScreen.route + "?recipe=" +viewModel.gson.toJson(viewModel.savedRecipe) )
                },
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .background(MaterialTheme.colorScheme.background)
            )
        },
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
                onClick = { viewModel.changeMod() }
            ) {
                Icon(
                    imageVector = if (viewModel.state.value.loadingType == LoadingType.LoadFromLocal) Icons.Default.Save else Icons.Default.NetworkWifi,
                    contentDescription = null
                )
            }
        },
        content = { padding ->

            Box(
                Modifier.padding(top = padding.calculateTopPadding())
            ) {
                if (viewModel.state.value.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else if (viewModel.state.value.recipes.isEmpty()) {
                    Icon(
                        imageVector = Icons.Default.LocalPizza,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(50.dp)
                    )
                }
                LazyColumn(
                    modifier
                        .fillMaxSize()
                        .padding(horizontal = 5.dp)
                ) {
                    item {
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                    items(viewModel.state.value.recipes) { recipe ->
                        Recipe(
                            recipe = recipe,
                            isLoadedFromLocal = viewModel.state.value.loadingType==LoadingType.LoadFromLocal,
                            onDeleteClicked = {viewModel.deleteRecipe(it)},
                            onSaveClicked = { _recipe ->
                                viewModel.savedRecipe = _recipe
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        duration = SnackbarDuration.Short,
                                        message  = "SavedRecipe set to ${viewModel.savedRecipe.title.let { if (it == "") null else it }}"
                                    )
                                }
                            })
                        Spacer(modifier = Modifier.height(5.dp))
                    }

                }
            }
        }
    )


}

