package com.example.heady.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Category RealmModel
 * Created by punitdama on 12/12/17.
 */
open class Category(
        @PrimaryKey
        var id : Int = 0,
        var name : String = "",
        var banner_url : String = "",
        var products : RealmList<Product> = RealmList(),
        var child_categories : RealmList<Int> = RealmList()
) : RealmObject()

const val CATEGORY_ID = "id"
const val CATEGORY_NAME = "name"
const val CATEGORY_PRODUCTS = "products"
const val CATEGORY_CHILD_CATEGORIES = "child_categories"