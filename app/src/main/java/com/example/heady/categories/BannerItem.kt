package com.example.heady.categories

import com.bumptech.glide.Glide
import com.example.heady.R
import com.example.heady.databinding.ItemBannerBinding
import com.example.heady.model.Category
import com.xwray.groupie.Item

/**
 * ViewHolder used to display banner item for Parent Categories
 * Created by punitdama on 13/12/17.
 */
class BannerItem(val category : Category,val clickManager : BannerClickManager) : Item<ItemBannerBinding>(){

    override fun getLayout() = R.layout.item_banner


    override fun bind(viewBinding: ItemBannerBinding, position: Int) {
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

interface BannerClickManager{
    fun openSubCategory(category: Category)
}