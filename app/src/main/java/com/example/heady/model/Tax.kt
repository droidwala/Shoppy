package com.example.heady.model

import io.realm.RealmObject

/**
 * Tax RealmModel
 * Defines Tax on product
 * Created by punitdama on 13/12/17.
 */
open class Tax(
    var name: String = "",
    var value: Float = 0.0f
) : RealmObject()