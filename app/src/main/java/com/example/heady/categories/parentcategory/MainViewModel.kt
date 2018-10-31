package com.example.heady.categories.parentcategory

import com.example.heady.Repository
import com.example.heady.model.ApiResponse
import com.example.heady.utils.URL
import rx.Observable
import rx.Single
import rx.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * ViewModel associated with MainActivity
 * Created by punitdama on 12/12/17.
 */
class MainViewModel @Inject constructor(val repository: Repository) {

    private val viewState: PublishSubject<MainViewState> = PublishSubject.create()

    // Calls
    fun fetchData(): Single<ApiResponse> {
        return repository.fetchParentCategories(URL)
                .doOnSubscribe { loadingStarted() }
                .doOnError { error -> loadingError(error) }
                .doOnSuccess(this::loadingFinished)
    }

    fun viewState(): Observable<MainViewState> {
        return viewState
    }

    // ViewState mutation methods.
    // We could use state reducer to replace this methods
    private fun loadingStarted() {
        Timber.d("loadingStarted called")
        viewState.onNext(MainViewState(true, null, null))
    }

    private fun loadingError(e: Throwable) {
        Timber.d("loaingError called")
        viewState.onNext(MainViewState(false, e.message, null))
    }

    private fun loadingFinished(response: ApiResponse) {
        Timber.d("loadingFinished called")
        viewState.onNext(MainViewState(false, null, response))
    }
}