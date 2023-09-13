package com.example.myrecipeapp.feature_recipe.presentation.addEditRecipe.components

import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun StringList(
    modifier: Modifier = Modifier,
    stringList: List<String> = listOf(),
    label: String,
    onListChange: (Int, String) -> Unit = { i: Int, s: String -> },
    onAddButton: () -> Unit = {},
    onDelete: (index: Int) -> Unit = {}
) {
        val lazyColumnListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            state = lazyColumnListState,
            contentPadding = PaddingValues(10.dp),
            modifier = modifier
                .fillMaxWidth()
                .border(2.dp, MaterialTheme.colorScheme.outline)

        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${label}s",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }
                
            }
            itemsIndexed(stringList) { index, item ->
                val isError = item.isEmpty() or item.isBlank()
                TextField(
                    value = item,
                    label = { Text("$label ${index + 1}") },
                    placeholder = { Text("Enter your $label here!") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = isError,
                    singleLine = true,
                    supportingText = { if (isError) Text(text = "Should not be empty")},
                    onValueChange = { newValue -> onListChange(index, newValue) },
                    trailingIcon = {
                        Row {
                            if (isError) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    tint = Color.Red,
                                    contentDescription = null,
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                modifier = Modifier.clickable { onDelete(index) }
                            )
                        }
                    }
                )
            }
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(TextFieldDefaults.MinHeight)
                        .clickable {
                            coroutineScope.launch {
                                onAddButton()
                                lazyColumnListState.scrollToItem(
                                    lazyColumnListState.layoutInfo.totalItemsCount - 1,
                                )
                            }
                        }
                ) {
                    Spacer(modifier = Modifier.height(7.dp))
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = null,

                        modifier = Modifier
                            .size(32.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }

            }
        }
    }


@Preview
@Composable
fun StringListPreview() {
    val sampleList = remember { mutableStateListOf("First", "Second", "Third") }

    StringList(
        stringList = sampleList,
        label = "Ingredient",
        onAddButton = { sampleList.add("") },
        onListChange = { index: Int, string: String -> sampleList[index] = string },
        onDelete = {index -> sampleList.removeAt(index)}
    )
}