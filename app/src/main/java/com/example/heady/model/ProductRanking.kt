package com.example.heady.model

import io.realm.RealmObject

/**
 * ProductRanking RealmModel
 * Defines various counts of the product like view_count,order_count,shares,etc.
 * Created by punitdama on 13/12/17.
 */
open class ProductRanking(
    var id: Int = 0,
    var view_count: Long = 0,
    var order_count: Long = 0,
    var shares: Long = 0
) : RealmObject()