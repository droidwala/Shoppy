package com.example.heady.categories

import com.example.heady.Repository
import com.example.heady.model.ApiResponse
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by punitdama on 12/12/17.
 */
const val URL = "https://stark-spire-93433.herokuapp.com/json"
class MainViewModel @Inject constructor(val repository: Repository){

    private val viewState : PublishSubject<MainViewState> = PublishSubject.create()

    fun fetchData() : Single<ApiResponse>{
        return fetchDataFromNetwork(URL)
                .doOnSubscribe { loadingStarted()}
                .doOnError{error -> loadingError(error)}
                .doOnSuccess(this::loadingFinished)
    }

    fun viewState() : Observable<MainViewState>{
        return viewState
    }


    private fun fetchDataFromNetwork(url : String) : Single<ApiResponse>{
        return repository.fetchDataFromNetwork(url)
    }

    private fun fetchDataFromDb() : Single<ApiResponse>{
        return repository.fetchDataFromDb()
    }


    private fun loadingStarted(){
        viewState.onNext(MainViewState(true,null,null))
    }

    private fun loadingError(e: Throwable){
        viewState.onNext(MainViewState(false,e.message,null))
    }

    private fun loadingFinished(response : ApiResponse){
        viewState.onNext(MainViewState(false,null,response))
    }



}