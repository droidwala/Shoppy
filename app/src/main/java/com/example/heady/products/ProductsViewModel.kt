package com.example.heady.products

import com.example.heady.Repository
import com.example.heady.model.Product
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by punitdama on 14/12/17.
 */
class ProductsViewModel @Inject constructor(val repository: Repository){

    private val viewState : PublishSubject<ProductsViewState> = PublishSubject.create()


    fun fetchData(parent_id : Int) : Single<List<Product>>{
        return fetchProducts(parent_id)
                .doOnSubscribe { loadingStarted() }
                .doOnError { error -> loadingError(error) }
                .doOnSuccess(this::loadingFinished)
    }

    fun viewState() : Observable<ProductsViewState>{
        return viewState
    }

    private fun fetchProducts(parent_id: Int) : Single<List<Product>>{
        return repository.fetchProducts(parent_id)
    }

    private fun loadingStarted(){
        viewState.onNext(ProductsViewState(true,null,null))
    }

    private fun loadingError(e: Throwable){
        viewState.onNext(ProductsViewState(true,e.message,null))
    }

    private fun loadingFinished(products : List<Product>){
        viewState.onNext(ProductsViewState(false,null,products))
    }
}