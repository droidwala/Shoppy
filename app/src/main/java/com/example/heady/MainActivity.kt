package com.example.heady

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_with_title.*

/**
 * Created by punitdama on 12/12/17.
 */
class MainActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        page_title.text = getString(R.string.app_name)

        Glide.with(this)
                .load(MENS_WEAR_BANNER_URL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(banner_1)

        Glide.with(this)
                .load(ELECTRONICS_BANNER_URL)
                .placeholder(R.drawable.ic_launcher_background)
                .into(banner_2)

        banner_1.setOnClickListener{
            startActivity(Intent(this,ChildCategoriesActivity::class.java))
        }

        banner_2.setOnClickListener{
            startActivity(Intent(this,ChildCategoriesActivity::class.java))
        }
    }
}