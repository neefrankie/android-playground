package com.github.neefrankie.servicetest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MyService : Service(), AnkoLogger {

    private val mBinder = DownloadBinder()

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onCreate() {
        super.onCreate()
        info("onCreate executed")

    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        info("onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        info("onDestroy executed")
    }

    inner class DownloadBinder : Binder() {
        fun startDownload() {
            info("startDownload executed")
        }

        fun getProgress(): Int {
            info("getProgress executed")

            return 0
        }
    }
}
