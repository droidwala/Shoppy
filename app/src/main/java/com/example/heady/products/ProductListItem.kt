package com.example.heady.products

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.example.heady.R
import com.example.heady.databinding.ItemListProductBinding
import com.example.heady.model.Product
import com.xwray.groupie.Item

/**
 * Created by punitdama on 14/12/17.
 */
class ProductListItem(val product : Product) : Item<ItemListProductBinding>(){

    override fun getLayout() = R.layout.item_list_product

    override fun bind(viewBinding: ItemListProductBinding, position: Int) {
        Glide.with(viewBinding.root.context)
                .load(product.image_url)
                .placeholder(R.drawable.banner_placeholder)
                .into(viewBinding.productImage)

        viewBinding.productName.text = product.name
        viewBinding.productPrice.text = viewBinding.root.context.getString(R.string.product_price,product.variants[0]?.price.toString())
        viewBinding.productSize.text = viewBinding.root.context.getString(R.string.product_size,product.variants[0]?.size.toString())

        viewBinding.productColors.removeAllViews()
        for(variant in product.variants){
            val view = View(viewBinding.root.context)
            val layoutParams = LinearLayout.LayoutParams(48,48)
            layoutParams.setMargins(0,0,8,0)
            view.layoutParams = layoutParams
            view.setPadding(8,8,8,8)
            view.setBackgroundColor(Color.parseColor(colorMap[variant.color]?: "#000000"))
            viewBinding.productColors.addView(view)
        }
    }
}

val colorMap = mapOf("Blue" to "#0000ff",
        "Red" to "#ff0000",
        "White" to "#F3F3F3",
        "Black" to "#000000",
        "Brown" to "#D2691E",
        "Yellow" to "#ffff00",
        "Light Blue" to "#ADD8E6",
        "Gold" to "#D4AF37",
        "Silver" to "#C0C0C0")