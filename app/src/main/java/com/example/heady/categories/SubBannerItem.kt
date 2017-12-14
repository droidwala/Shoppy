package com.example.heady.categories

import com.bumptech.glide.Glide
import com.example.heady.R
import com.example.heady.databinding.ItemChildBannerBinding
import com.example.heady.databinding.ItemSubBannerBinding
import com.example.heady.model.Category
import com.xwray.groupie.Item

/**
 * ViewHolder used to display items for sub-categories under child categories
 * Created by punitdama on 14/12/17.
 */
class SubBannerItem(val category : Category, val clickManager : BannerClickManager) : Item<ItemSubBannerBinding>(){
    override fun getLayout() = R.layout.item_sub_banner


    override fun bind(viewBinding: ItemSubBannerBinding, position: Int) {
        Glide.with(viewBinding.root.context)
                .load(category.banner_url)
                .placeholder(R.drawable.banner_placeholder)
                .into(viewBinding.banner)

        viewBinding.categoryName.text = category.name

        viewBinding.parent.setOnClickListener {
            clickManager.openSubCategory(category)
        }

    }

}