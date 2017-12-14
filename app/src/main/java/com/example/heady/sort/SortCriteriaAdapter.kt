package com.example.heady.sort

import com.example.heady.utils.MOST_ORDERED_PRODUCTS
import com.example.heady.utils.MOST_SHARED_PRODUCTS
import com.example.heady.utils.MOST_VIEWED_PRODUCTS
import com.xwray.groupie.GroupAdapter

/**
 * Created by punitdama on 14/12/17.
 */
class SortCriteriaAdapter(val selected_criteria : String?,val clickManager: SortCriteriaClickManager) : GroupAdapter(){
    private val sort_criterias = listOf(MOST_VIEWED_PRODUCTS, MOST_ORDERED_PRODUCTS, MOST_SHARED_PRODUCTS)
    init {
        addSortChoices()
    }

    private fun addSortChoices(){
        if(selected_criteria !=null) {
            for (criteria in sort_criterias) {
                if(selected_criteria == criteria){
                    add(SortCriteriaItem(criteria,true,clickManager))
                }
                else{
                    add(SortCriteriaItem(criteria,false,clickManager))
                }
            }
        }
        else{
            for(criteria in sort_criterias){
                add(SortCriteriaItem(criteria,false,clickManager))
            }
        }
    }
}