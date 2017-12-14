package com.example.heady

import com.example.heady.model.ApiResponse
import com.example.heady.model.Category
import com.example.heady.model.Product
import com.example.heady.utils.BANNER_MAP
import com.example.heady.utils.IMAGE_MAP
import io.realm.Realm
import rx.Single
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by punitdama on 12/12/17.
 */

class Repository @Inject constructor(val apiService: ApiService,val realmService: RealmService){

    fun fetchParentCategoriesFromNetwork(url: String) : Single<ApiResponse>
            = apiService.get(url)

    fun fetchParentCategoriesFromDb() : Single<ApiResponse>
             = realmService.fetchParentCategories()

    fun fetchChildCategories(parent_id : Int) : Single<List<Category>>
            = realmService.fetchChildCategories(parent_id)

    fun fetchProducts(parent_id: Int) : Single<List<Product>>
            = realmService.fetchProducts(parent_id)

    fun saveDataInDb(apiResponse: ApiResponse){
        val realmDb = Realm.getDefaultInstance()
        Timber.d("Saving Data in Db")
        realmDb.executeTransaction {
            realm ->
            for(category in apiResponse.categories){
                category.banner_url = BANNER_MAP[category.name]?: ""
                for(index in category.products.indices) {
                    category.products[index]?.image_url = IMAGE_MAP[category.name] ?: ""
                }
                realm.insertOrUpdate(category)
            }

            for(ranking in apiResponse.rankings){
                realm.insertOrUpdate(ranking)
            }

        }

        Timber.d("Data saved in Db!!")

        realmDb.close()
    }
}