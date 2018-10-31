package com.example.heady.products

import com.bumptech.glide.Glide
import com.example.heady.R
import com.example.heady.databinding.ItemGridProductBinding
import com.example.heady.model.Product
import com.xwray.groupie.Item

/**
 * ViewHolder showing individual Product item
 * Created by punitdama on 14/12/17.
 */

class ProductGridItem(val product: Product) : Item<ItemGridProductBinding>() {

    override fun getLayout() = R.layout.item_grid_product

    override fun bind(viewBinding: ItemGridProductBinding, position: Int) {
        Glide.with(viewBinding.root.context)
                .load(product.image_url)
                .placeholder(R.drawable.banner_placeholder)
                .into(viewBinding.productImage)

        viewBinding.productName.text = product.name
        viewBinding.productPrice.text = viewBinding.root.context.getString(R.string.product_price, product.variants[0]?.price.toString())
    }
}