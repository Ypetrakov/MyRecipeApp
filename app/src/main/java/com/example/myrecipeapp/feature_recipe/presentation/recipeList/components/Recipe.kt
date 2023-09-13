package com.example.myrecipeapp.feature_recipe.presentation.recipeList.components

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myrecipeapp.R
import com.example.myrecipeapp.feature_recipe.domain.model.Recipe
import com.example.myrecipeapp.ui.theme.MyRecipeAppTheme

@Composable
fun Recipe(
    recipe: Recipe,
    modifier: Modifier = Modifier,
    onSaveClicked: (Recipe) -> Unit,
    isLoadedFromLocal: Boolean = false,
    onDeleteClicked: (Recipe) -> Unit = {}
) {
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .border(BorderStroke(1.dp, Color.Black), MaterialTheme.shapes.large)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(8.dp)
            .animateContentSize(),

        ) {
        Row {
            if(isLoadedFromLocal) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                    modifier = Modifier
                        .clickable { onDeleteClicked(recipe) }
                )
            }
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .clickable { onSaveClicked(recipe) }
            )
        }

        Spacer(modifier = Modifier.height(3.dp))
        Divider(color = MaterialTheme.colorScheme.onSecondaryContainer)
        Spacer(modifier = Modifier.height(3.dp))


        Text(
            buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.titleMedium.toSpanStyle().copy(
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                ) {
                    append("Servings: ")
                }
                withStyle(
                    style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                ) {
                    append(recipe.servings)
                }
            })
        
        Spacer(modifier = Modifier.height(3.dp))
        Divider(color = MaterialTheme.colorScheme.onSecondaryContainer)
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            buildAnnotatedString {
                withStyle(
                    style = MaterialTheme.typography.titleMedium.toSpanStyle().copy(
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                ) {
                    append("Ingredients: ")
                }
                withStyle(
                    style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    ),
                ) {
                    append(recipe.ingredients.joinToString("; "),)
                }
            })
        Spacer(modifier = Modifier.height(3.dp))
        Divider(color = MaterialTheme.colorScheme.onSecondaryContainer)
        Spacer(modifier = Modifier.height(3.dp))

        if (expanded) {
            Text(
                text = "Recipe",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            recipe.instructions.forEachIndexed { index, instruction ->
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = MaterialTheme.typography.titleMedium.toSpanStyle().copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                        ) {
                            append("${index + 1} - ")
                        }
                        withStyle(
                            style = MaterialTheme.typography.bodyLarge.toSpanStyle().copy(
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            ),
                        ) {
                            append(instruction)
                        }
                    })
            }
        }
        Icon(
            painter = painterResource(
                id = if (!expanded) {
                    R.drawable.baseline_expand_more_24
                } else {
                    R.drawable.baseline_expand_less_24
                }
            ),
            tint = MaterialTheme.colorScheme.onTertiaryContainer,
            contentDescription = null,
            modifier = Modifier
                .align(CenterHorizontally)
                .clickable {
                    expanded = !expanded
                }
        )
    }
}

@Preview(widthDp = 320, heightDp = 320)
@Composable
fun RecipePreview() {
    MyRecipeAppTheme {
        val recipe = Recipe(
            title = "Stracciatella",
            ingredients = listOf(
                "Ingredient 1",
                "Ingredient 2",
                "Ingredient 3",
                "Ingredient 4"
            ),
            category = "Unknown",
            servings = "4",
            instructions = listOf("Step 1", "Step 2", "Step 3"),
        )
        val context = LocalContext.current

        Recipe(
            recipe = recipe,
            onSaveClicked = { _recipe ->
                Toast.makeText(context, _recipe.title, Toast.LENGTH_SHORT).show()
            }
        )
    }

}