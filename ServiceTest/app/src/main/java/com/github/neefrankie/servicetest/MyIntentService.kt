package com.github.neefrankie.servicetest

import android.app.IntentService
import android.content.Intent
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MyIntentService : IntentService("MyIntentService"), AnkoLogger {
    override fun onHandleIntent(intent: Intent?) {
        info("Thread id is ${Thread.currentThread().id}")
    }

    override fun onDestroy() {
        super.onDestroy()
        info("onDestroy executed")
    }
}