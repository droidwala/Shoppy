package com.example.heady.model

/**
 * Data class used for representing API response fetched from Network
 * Created by punitdama on 12/12/17.
 */

data class ApiResponse(var categories: List<Category>, var rankings: List<Ranking> = emptyList())