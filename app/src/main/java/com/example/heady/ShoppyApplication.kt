package com.example.heady

import com.example.heady.di.AppComponent
import com.example.heady.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.realm.Realm
import io.realm.RealmConfiguration
import timber.log.Timber

/**
 * Base Application
 * Screens order:
 * SplashActivity -> ParentCategoryActivity -> ChildCategoryActivity -> SubCategoryActivity ->
 * ProductsActivity -> ProductDetailActivity(left to be implemented)
 * Created by punitdama on 12/12/17.
 */

class ShoppyApplication : DaggerApplication() {

    lateinit var component: AppComponent
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(realmConfig)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerAppComponent.builder().application(this).build()
        return component
    }
}