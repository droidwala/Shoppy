package com.example.heady.categories

import com.example.heady.model.Category
import com.xwray.groupie.GroupAdapter

/**
 * Recylerview Adapter used to display Banner Images for various kind of Categories like Parent,Child,Sub,etc.
 * Created by punitdama on 13/12/17.
 */
class BannerAdapter(val clickManager : BannerClickManager) : GroupAdapter(){

    fun addItems(categories : List<Category>){
        for(category in categories){
            add(BannerItem(category,clickManager))
        }
    }

    fun addChildBannerItems(categories: List<Category>){
        for(category in categories){
            add(ChildBannerItem(category, clickManager))
        }
    }

    fun addSubBannerItems(categories: List<Category>) {
        for(category in categories){
            add(SubBannerItem(category,clickManager))
        }
    }

}