package com.example.heady.products

import com.example.heady.Repository
import com.example.heady.model.Product
import com.example.heady.utils.CLEAR_SORT_FLAGS
import com.example.heady.utils.MOST_ORDERED_PRODUCTS
import com.example.heady.utils.MOST_SHARED_PRODUCTS
import com.example.heady.utils.MOST_VIEWED_PRODUCTS
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * ViewModel associated with ProductsActivity
 * Created by punitdama on 14/12/17.
 */
class ProductsViewModel @Inject constructor(val repository: Repository) {

    private val viewState: PublishSubject<ProductsViewState> = PublishSubject.create()

    fun fetchData(parent_id: Int): Single<List<Product>> {
        return fetchProducts(parent_id).compose(sideEffect())
    }

    fun sortData(parent_id: Int, query: String): Single<List<Product>> {
        return when (query) {
            MOST_VIEWED_PRODUCTS -> sortByMostViewedProducts(parent_id, query).compose(sideEffect())
            MOST_ORDERED_PRODUCTS -> sortByMostOrderedProducts(parent_id, query).compose(sideEffect())
            MOST_SHARED_PRODUCTS -> sortByMostSharedProducts(parent_id, query).compose(sideEffect())
            CLEAR_SORT_FLAGS -> fetchData(parent_id)
            else -> throw IllegalArgumentException("Invalid sort criteria selected")
        }
    }

    fun viewState(): Observable<ProductsViewState> {
        return viewState
    }

    private fun fetchProducts(parent_id: Int): Single<List<Product>> {
        return repository.fetchProducts(parent_id)
    }

    private fun sortByMostViewedProducts(parent_id: Int, query: String): Single<List<Product>> {
        return repository.sortByMostViewedProducts(parent_id, query)
    }

    private fun sortByMostOrderedProducts(parent_id: Int, query: String): Single<List<Product>> {
        return repository.sortByMostOrderedProducts(parent_id, query)
    }

    private fun sortByMostSharedProducts(parent_id: Int, query: String): Single<List<Product>> {
        return repository.sortByMostSharedProducts(parent_id, query)
    }

    private fun loadingStarted() {
        viewState.onNext(ProductsViewState(true, null, null))
    }

    private fun loadingError(e: Throwable) {
        viewState.onNext(ProductsViewState(true, e.message, null))
    }

    private fun loadingFinished(products: List<Product>) {
        viewState.onNext(ProductsViewState(false, null, products))
    }

    private fun sideEffect(): (Single<List<Product>>) -> Single<List<Product>> {
        return {
            single ->
            single.doOnSubscribe { loadingStarted() }
                    .doOnError { error -> loadingError(error) }
                    .doOnSuccess(this::loadingFinished)
        }
    }
}