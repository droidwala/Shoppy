package com.example.heady.products

import com.example.heady.model.Product
import com.xwray.groupie.GroupAdapter

/**
 * Created by punitdama on 14/12/17.
 */
class ProductsAdapter(val isGridLayout : Boolean = true) : GroupAdapter(){

    fun addProducts(products : List<Product>){
        clear()
        if(isGridLayout) {
            for (product in products) {
                add(ProductGridItem(product))
            }
        }
        else{
            for(product in products){
                add(ProductListItem(product))
            }
        }
    }

}