package com.example.heady.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by punitdama on 13/12/17.
 */
open class Product(
        @PrimaryKey
        var id : Int = 0,
        var name : String = "",
        var image_url : String = "",
        var variants : RealmList<Variant> = RealmList(),
        var tax : Tax? = null
) : RealmObject()