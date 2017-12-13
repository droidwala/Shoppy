package com.example.heady.utils

import android.content.Context
import android.widget.Toast
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by punitdama on 12/12/17.
 */

fun Context.toast(message : String,length : Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,message,length).show()
}

operator fun CompositeSubscription.plusAssign(subscription: Subscription){
    add(subscription)
}

