package com.example.heady

import com.example.heady.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by punitdama on 12/12/17.
 */

class ShoppyApplication : DaggerApplication(){

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

}