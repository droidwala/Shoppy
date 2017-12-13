package com.example.heady.intro

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.heady.R
import com.example.heady.categories.parentcategory.MainActivity
import rx.Single
import rx.Subscription
import java.util.concurrent.TimeUnit

/**
 * Created by punitdama on 12/12/17.
 */
class SplashActivity : Activity(){

    private var subscription : Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        //Mocking 1 sec delay in Splash screen
        subscription = Single.just(true)
                .delay(1,TimeUnit.SECONDS)
                .subscribe({
                    takeUserInsideApp()
                }, {
                    takeUserInsideApp()
                })

    }

    override fun onPause() {
        super.onPause()
        subscription?.unsubscribe()
    }

    private fun takeUserInsideApp(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}