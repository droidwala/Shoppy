package com.example.heady.di

import android.app.Application
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.lang.reflect.Modifier
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * We use this to bind our App class as a Context in the AppComponent
 * Since we are using Dagger Android we do not need to pass our Application instance to any module
 * as was done in previous versions of dagger,
 * we simply need to expose our Application as Context and Dagger Android will inject it for us
 * Created by punitdama on 12/12/17.
 */
@Module
abstract class AppModule{
    @Binds
    abstract fun bindContext(app: Application) : Context
}