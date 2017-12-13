package com.example.heady.model

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by punitdama on 12/12/17.
 */
open class Ranking(
        @PrimaryKey
        var ranking : String = "",
        var products : RealmList<ProductRanking> = RealmList()
) : RealmObject()