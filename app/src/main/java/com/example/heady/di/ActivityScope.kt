package com.example.heady.di

import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope

/**
 * Created by punitdama on 13/12/17.
 */
@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
annotation class ActivityScope