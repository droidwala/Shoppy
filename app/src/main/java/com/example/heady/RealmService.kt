package com.example.heady

import com.example.heady.model.ApiResponse
import com.example.heady.model.CATEGORY_CHILD_CATEGORIES
import com.example.heady.model.CATEGORY_NAME
import com.example.heady.model.Category
import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by punitdama on 13/12/17.
 */
@Singleton
class RealmService @Inject constructor(){

    fun fetchParentCategories() : Single<ApiResponse>{
        return Single.fromCallable {
            val realmDb = Realm.getDefaultInstance()

            val categories = realmDb.where<Category>()
                    .`in`(CATEGORY_NAME, arrayOf("Mens Wear","Electronics"))
                    .findAll()


            val result = realmDb.copyFromRealm(categories)

            realmDb.close()
            return@fromCallable ApiResponse(result)
        }
    }

}