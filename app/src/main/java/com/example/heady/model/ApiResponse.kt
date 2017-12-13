package com.example.heady.model

/**
 * Created by punitdama on 12/12/17.
 */

data class ApiResponse(var categories : List<Category>,var rankings : List<Ranking> = emptyList())