package com.github.neefrankie.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainActivity : AppCompatActivity(), AnkoLogger {

    private var downloadBinder: MyService.DownloadBinder? = null

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service is MyService.DownloadBinder) {
                downloadBinder = service
                downloadBinder?.startDownload()
                downloadBinder?.getProgress()
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_service.setOnClickListener {
            val startIntent = Intent(this, MyService::class.java)
            startService(startIntent)
        }

        stop_service.setOnClickListener {
            val stopIntent = Intent(this, MyService::class.java)
            stopService(stopIntent)
        }

        bind_service.setOnClickListener {
            val bindIntent = Intent(this, MyService::class.java)
            bindService(bindIntent, connection, Context.BIND_AUTO_CREATE)
        }

        unbind_service.setOnClickListener {
            unbindService(connection)
        }

        start_intent_service.setOnClickListener {
            info("Thread id is ${Thread.currentThread().id}")

            val intentService = Intent(this, MyIntentService::class.java)
            startService(intentService)
        }
    }
}
