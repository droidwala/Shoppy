package com.example.heady

import com.example.heady.model.ApiResponse
import com.example.heady.model.CATEGORY_ID
import com.example.heady.model.CATEGORY_NAME
import com.example.heady.model.Category
import io.realm.Realm
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

    fun fetchChildCategories(parent_id : Int) : Single<List<Category>>{
        return Single.fromCallable {
            val realmDb = Realm.getDefaultInstance()
            val parent_category = realmDb.where<Category>()
                    .equalTo(CATEGORY_ID, parent_id)
                    .findFirst()

            val result = realmDb.copyFromRealm(parent_category!!)

            realmDb.close()
            result.child_categories
        }.flatMap { category_ids ->
            val realmDb = Realm.getDefaultInstance()
            val filter = arrayOfNulls<Int>(category_ids.size)
            category_ids.toArray(filter)

            val child_categories = realmDb.where<Category>()
                    .`in`(CATEGORY_ID,filter)
                    .findAll()

            val result = realmDb.copyFromRealm(child_categories)

            realmDb.close()
            return@flatMap Single.just(result)
        }
    }

}