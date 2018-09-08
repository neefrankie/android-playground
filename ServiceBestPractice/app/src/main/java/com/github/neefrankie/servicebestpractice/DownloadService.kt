package com.github.neefrankie.servicebestpractice

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.support.v4.app.NotificationCompat
import org.jetbrains.anko.notificationManager

class DownloadService : Service() {

    private var downloadTask: DownloadTask? = null
    private var downloadUrl: String? = null
    private val listener: DownloadListener = object : DownloadListener {
        override fun onProgress(progress: Int) {

        }

        override fun onSuccess() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onFailed() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onPaused() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onCanceled() {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private fun getNotification(title: String, progress: Int): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, 0, intent, 0)
        val builder = NotificationCompat.Builder(this)
    }
    inner class DownloadBinder : Binder() {
        fun startDownload(url: String) {
            if (downloadTask == null) {
                downloadUrl = url
                downloadTask = DownloadTask(listener)
                downloadTask.execute(downloadUrl)
            }
        }

        fun pauseDownload() {
            downloadTask?.pauseDownload()
        }

        fun cancelDownload() {
            downloadTask?.cancelDownload()

            if (downloadUrl != null) {

            }
        }
    }
}