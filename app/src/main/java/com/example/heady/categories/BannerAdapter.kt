package com.example.heady.categories

import com.example.heady.model.Category
import com.xwray.groupie.GroupAdapter

/**
 * Created by punitdama on 13/12/17.
 */
class BannerAdapter(val clickManager : BannerClickManager) : GroupAdapter(){

    fun addItems(categories : List<Category>){
        for(category in categories){
            add(BannerItem(category,clickManager))
        }
    }

}