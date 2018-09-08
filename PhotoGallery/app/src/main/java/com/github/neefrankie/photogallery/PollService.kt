package com.github.neefrankie.photogallery

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class PollService : IntentService("PollService"), AnkoLogger {
    override fun onHandleIntent(intent: Intent?) {
        info("Received an intent: $intent")
    }

    private fun isNetworkAvailableAndConnected(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isNetworkAvailable = cm.activeNetworkInfo != null
        return isNetworkAvailable && cm.activeNetworkInfo.isConnected
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PollService::class.java)
        }
    }
}