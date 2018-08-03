package com.example.heady.utils

import android.content.Context
import android.support.v4.app.Fragment
import com.example.heady.ShoppyApplication
import com.example.heady.di.AppComponent
import kotlin.LazyThreadSafetyMode.NONE

inline fun <reified T> Fragment.inject(
    noinline initializer : AppComponent.() -> T) = lazy{
  context?.let {
    (it.applicationContext as ShoppyApplication).component.initializer()
  } ?: throw IllegalStateException("Context must be set before accessing this property")
}


inline fun <reified T> Context.inject(
    noinline initializer : AppComponent.() -> T) = lazy(NONE){
  (applicationContext as ShoppyApplication).component.initializer()
}