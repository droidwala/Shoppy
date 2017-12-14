package com.example.heady

import android.support.v4.util.Pair
import com.example.heady.model.*
import io.realm.Realm
import io.realm.kotlin.where
import rx.Single
import timber.log.Timber
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

    fun fetchProducts(parent_id: Int) : Single<List<Product>>{
        return Single.fromCallable {
            val realmDb = Realm.getDefaultInstance()
            val category = realmDb.where<Category>()
                    .equalTo(CATEGORY_ID,parent_id)
                    .findFirst()

            val result = realmDb.copyFromRealm(category);
            realmDb.close()
            return@fromCallable result?.products
        }
    }

    fun sortProductsByViewCount(parent_id: Int, query : String) : Single<List<Product>>{
       return Single.zip(fetchProductRankings(query),
                fetchProducts(parent_id),
                this::generatePair)
                .flatMap(this::sortByViewCount)
    }

    fun sortProductsByOrderCount(parent_id: Int, query: String) : Single<List<Product>>{
        return Single.zip(
                fetchProductRankings(query),
                fetchProducts(parent_id),
                this::generatePair)
                .flatMap(this::sortByOrderCount)
    }

    fun sortProductsByShareCount(parent_id: Int, query: String) : Single<List<Product>>{
        return Single.zip(
                fetchProductRankings(query),
                fetchProducts(parent_id),
                this::generatePair)
                .flatMap(this::sortBySharesCount)
    }

    private fun fetchProductRankings(query : String) : Single<MutableMap<Int,ProductRanking>>{
        return Single.fromCallable {
            val realmDb = Realm.getDefaultInstance()
            val ranking_realm = realmDb.where<Ranking>()
                    .equalTo("ranking",query)
                    .findFirst()

            val result = realmDb.copyFromRealm(ranking_realm)

            val product_rankings = mutableMapOf<Int,ProductRanking>()
            for(product in result!!.products){
                product_rankings[product.id] = product
            }

            realmDb.close()
            return@fromCallable product_rankings
        }
    }

    private fun generatePair(product_rankings : MutableMap<Int,ProductRanking>, products : List<Product>)
            = Pair.create(product_rankings,products)

    private fun sortByViewCount(pair : Pair<MutableMap<Int,ProductRanking>,List<Product>>) : Single<List<Product>>{
        val product_rankings = pair.first!!
        val products = pair.second!!

        for(index in products.indices){
            product_rankings[products[index].id]?.let {
                products[index].view_count = it.view_count
            }
        }

        return Single.just(products.sortedByDescending {
            it.view_count
        })
    }

    private fun sortByOrderCount(pair : Pair<MutableMap<Int,ProductRanking>,List<Product>>) : Single<List<Product>>{
        val product_rankings = pair.first!!
        val products = pair.second!!

        for(index in products.indices){
            product_rankings[products[index].id]?.let {
                products[index].order_count = it.order_count
            }
        }

        return Single.just(products.sortedByDescending {
            it.order_count
        })
    }

    private fun sortBySharesCount(pair : Pair<MutableMap<Int,ProductRanking>,List<Product>>) : Single<List<Product>>{
        val product_rankings = pair.first!!
        val products = pair.second!!

        for(index in products.indices){
            product_rankings[products[index].id]?.let {
                products[index].shares = it.shares
            }
        }

        return Single.just(products.sortedByDescending {
            it.shares
        })
    }



}