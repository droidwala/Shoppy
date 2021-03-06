package com.example.heady

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import rx.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ApiService class written instead of using Retrofit library
 * Since only 1 API call was involved it was better to just write simple helper using OkHTTP + RxJava to
 * fetch and parse API response
 * Created by punitdama on 12/12/17.
 */
@Singleton
class ApiService @Inject constructor(val client: OkHttpClient, val gson: Gson) {

    inline fun <reified T> get(url: String): Single<T> {
            return Single.fromCallable {
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    return@fromCallable gson.fromJson(response.body()?.string(), T::class.java)
                } else {
                    throw Exception("Error fetching data " + response.code().toString())
                }
            }
    }
}