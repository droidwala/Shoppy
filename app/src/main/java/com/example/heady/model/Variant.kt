package com.example.heady.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by punitdama on 13/12/17.
 */

open class Variant(
        @PrimaryKey
        var id : Int = 0,
        var color : String = "",
        var size : Int = 0,
        var price : Long = 0
) : RealmObject()