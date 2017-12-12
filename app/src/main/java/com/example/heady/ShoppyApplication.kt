package com.example.heady

import android.app.Application
import timber.log.Timber

/**
 * Created by punitdama on 12/12/17.
 */

class ShoppyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}