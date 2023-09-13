package com.example.myrecipeapp.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferences @Inject constructor(@ApplicationContext context: Context){

    private val prefs = context.getSharedPreferences(Constants.MY_SHARED_PREFERENCES, Context.MODE_PRIVATE)

    fun putCategory(categoryName: String) {
        prefs.edit()
            .putString(
                Constants.CATEGORY_TAG,
                addNewStringToString(Constants.CATEGORY_TAG, categoryName))
            .apply()
    }

    fun getCategories(): List<String> {
        return prefs.getString(Constants.CATEGORY_TAG, "")?.split(" ") ?: emptyList()
    }

    private fun addNewStringToString(tag: String, str: String): String {
        val oldString = prefs.getString(tag, "")
        if (oldString.isNullOrEmpty()) {
            return str
        }
        return oldString.plus(" ").plus(str)
    }



}