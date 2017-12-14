package com.example.heady.sort

/**
 * Used to communicate back sort criteria selected back to ProductsActivity
 * Created by punitdama on 14/12/17.
 */
interface SortCriteriaClickManager{
    fun sortBy(query : String)
}