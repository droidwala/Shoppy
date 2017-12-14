package com.example.heady.sort

import com.example.heady.R
import com.example.heady.databinding.ItemSortCriteriaBinding
import com.xwray.groupie.Item

/**
 * ViewHolder showing individual Sort Criteria Item
 * Created by punitdama on 14/12/17.
 */
class SortCriteriaItem(val sort_criteria : String, val is_checked : Boolean,val clickManager: SortCriteriaClickManager) : Item<ItemSortCriteriaBinding>(){

    override fun getLayout() = R.layout.item_sort_criteria
    override fun bind(viewBinding: ItemSortCriteriaBinding, position: Int) {
        viewBinding.sortCriteria.text = sort_criteria
        viewBinding.radioButton.isChecked = is_checked

        if (!is_checked) {
            viewBinding.root.setOnClickListener { clickManager.sortBy(sort_criteria) }
            viewBinding.radioButton.setOnCheckedChangeListener { _, _ -> clickManager.sortBy(sort_criteria) }

        }
    }




}