package com.example.heady

import com.example.heady.ApiService
import com.example.heady.model.ApiResponse
import rx.Single
import javax.inject.Inject

/**
 * Created by punitdama on 12/12/17.
 */

class Repository @Inject constructor(val apiService: ApiService){

    fun fetchDataFromNetwork(url: String) : Single<ApiResponse>
            = apiService.get(url)

    //TODO
    fun fetchDataFromDb() : Single<ApiResponse>
             = Single.just(null)
}