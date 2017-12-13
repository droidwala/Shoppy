package com.example.heady.categories.childcategory

import com.example.heady.Repository
import com.example.heady.model.ApiResponse
import com.example.heady.model.Category
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by punitdama on 13/12/17.
 */
class ChildCategoryViewModel @Inject constructor(val repository: Repository){

    private val viewState : PublishSubject<ChildCategoryViewState> = PublishSubject.create()

    fun fetchData(parent_id : Int) : Single<List<Category>> {
        return fetchChildCategories(parent_id)
                .doOnSubscribe { loadingStarted() }
                .doOnError{error -> loadingError(error)}
                .doOnSuccess(this::loadingFinished)
    }

    fun viewState() : Observable<ChildCategoryViewState>{
        return viewState
    }

    private fun fetchChildCategories(parent_id : Int) : Single<List<Category>>{
        return repository.fetchChildCategories(parent_id)
    }

    private fun loadingStarted(){
        viewState.onNext(ChildCategoryViewState(true,null,null))
    }

    private fun loadingError(e : Throwable){
        viewState.onNext(ChildCategoryViewState(false,e.message,null))
    }

    private fun loadingFinished(categories : List<Category>){
        viewState.onNext(ChildCategoryViewState(false,null,categories))
    }

}