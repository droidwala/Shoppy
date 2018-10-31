package com.example.heady.categories.parentcategory

import com.example.heady.model.ApiResponse

/**
 * Data class representing various View States of MainActivity
 * Created by punitdama on 13/12/17.
 */
data class MainViewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val response: ApiResponse? = null
)