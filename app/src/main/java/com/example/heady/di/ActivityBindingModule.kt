package com.example.heady.di

import com.example.heady.categories.childcategory.ChildCategoryActivity
import com.example.heady.categories.parentcategory.MainActivity
import com.example.heady.categories.subcategory.SubCategoryActivity
import com.example.heady.products.ProductsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by punitdama on 13/12/17.
 */
@Module
abstract class ActivityBindingModule{

    @ContributesAndroidInjector()
    abstract fun mainActivity() : MainActivity

    @ContributesAndroidInjector()
    abstract fun childCategoryActivity() : ChildCategoryActivity

    @ContributesAndroidInjector
    abstract fun subCategoryActivity() : SubCategoryActivity

    @ContributesAndroidInjector
    abstract fun productsActivity() : ProductsActivity

}