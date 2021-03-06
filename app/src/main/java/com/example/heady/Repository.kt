package com.example.heady

import com.example.heady.model.ApiResponse
import com.example.heady.model.Category
import com.example.heady.model.Product
import com.example.heady.utils.BANNER_MAP
import com.example.heady.utils.IMAGE_MAP
import com.example.heady.utils.URL
import io.realm.Realm
import rx.Single
import timber.log.Timber
import javax.inject.Inject

/**
 * Intermediary between viewModel and external API service/Db
 * Implemented so that viewModel is unaware of the source of Data
 * It is responsibility of Repository to decide where to fetch data for current screen
 * Single Repository was used since there was just single source of data
 * Created by punitdama on 12/12/17.
 */

class Repository @Inject constructor(val apiService: ApiService, val realmService: RealmService) {

    fun fetchParentCategories(url: String): Single<ApiResponse> {
        return Single.concat(
                fetchParentCategoriesFromDb().onErrorResumeNext(fetchParentCategoriesFromNetwork(url)),
                fetchParentCategoriesFromNetwork(URL))
                .takeFirst { response -> response != null && response.categories.isNotEmpty() }
                .toSingle()
    }

    fun fetchChildCategories(parent_id: Int): Single<List<Category>> =
            realmService.fetchChildCategories(parent_id)

    fun fetchProducts(parent_id: Int): Single<List<Product>> =
            realmService.fetchProducts(parent_id)

    fun sortByMostViewedProducts(parent_id: Int, query: String) =
            realmService.sortProductsByViewCount(parent_id, query)

    fun sortByMostOrderedProducts(parent_id: Int, query: String) =
            realmService.sortProductsByOrderCount(parent_id, query)

    fun sortByMostSharedProducts(parent_id: Int, query: String) =
            realmService.sortProductsByShareCount(parent_id, query)

    private fun fetchParentCategoriesFromNetwork(url: String): Single<ApiResponse> {
        return apiService.get<ApiResponse>(url)
                .doOnSuccess(this::saveDataInDb)
                .flatMap { fetchParentCategoriesFromDb() }
    }

    private fun fetchParentCategoriesFromDb(): Single<ApiResponse> =
            realmService.fetchParentCategories()

    private fun saveDataInDb(apiResponse: ApiResponse) {
        val realmDb = Realm.getDefaultInstance()
        Timber.d("Saving Data in Db")
        realmDb.executeTransaction {
            realm ->
            for (category in apiResponse.categories) {
                category.banner_url = BANNER_MAP[category.name] ?: ""
                for (index in category.products.indices) {
                    category.products[index]?.image_url = IMAGE_MAP[category.name] ?: ""
                }
                realm.insertOrUpdate(category)
            }

            for (ranking in apiResponse.rankings) {
                realm.insertOrUpdate(ranking)
            }
        }

        Timber.d("Data saved in Db!!")

        realmDb.close()
    }
}