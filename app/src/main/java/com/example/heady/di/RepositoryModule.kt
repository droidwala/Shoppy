package com.example.heady.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Module providing all Repository dependencies in form of Singleton
 * Created by punitdama on 13/12/17.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(app: Context): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)
                .addInterceptor(ChuckInterceptor(app))
                .build()
    }
}