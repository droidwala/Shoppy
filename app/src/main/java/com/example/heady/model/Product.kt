package com.example.heady.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

/**
 * Product RealmModel
 * Created by punitdama on 13/12/17.
 */
open class Product(
        @PrimaryKey
        var id : Int = 0,
        var name : String = "",
        var image_url : String = "",
        @Ignore var view_count : Long = 0,
        @Ignore var order_count : Long = 0,
        @Ignore var shares : Long = 0,
        var variants : RealmList<Variant> = RealmList(),
        var tax : Tax? = null
) : RealmObject()