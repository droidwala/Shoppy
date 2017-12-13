package com.example.heady.di

import com.example.heady.categories.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by punitdama on 13/12/17.
 */
@Module
abstract class ActivityBindingModule{

    @ContributesAndroidInjector()
    abstract fun mainActivity() : MainActivity

}