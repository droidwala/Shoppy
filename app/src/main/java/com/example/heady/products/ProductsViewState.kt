package com.example.heady.products

import com.example.heady.model.Product

/**
 * Created by punitdama on 14/12/17.
 */
data class ProductsViewState(
        val isLoading : Boolean = false,
        val error : String? = null,
        val products : List<Product>? = null
)