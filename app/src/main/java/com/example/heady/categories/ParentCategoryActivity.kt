package com.example.heady.categories

import android.app.Activity
import android.os.Bundle
import com.example.heady.R
import kotlinx.android.synthetic.main.toolbar_with_title.*

/**
 * Created by punitdama on 12/12/17.
 */
class ParentCategoryActivity : Activity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        page_title.text = getString(R.string.app_name)

    }
}