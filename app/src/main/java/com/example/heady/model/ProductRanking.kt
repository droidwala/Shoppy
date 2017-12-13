package com.example.heady.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by punitdama on 13/12/17.
 */
open class ProductRanking(
        @PrimaryKey
        var id : Int = 0,
        var view_count : Long = 0,
        var order_count : Long = 0,
        var shares : Long = 0
) : RealmObject()