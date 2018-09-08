package com.github.neefrankie.servicebestpractice

import android.os.AsyncTask
import android.os.Environment
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile

class DownloadTask(private val listener: DownloadListener) : AsyncTask<String, Int, Int>() {

    private var isCanceled = false
    private var isPaused = false
    private var lastProgress: Int = 0

    override fun doInBackground(vararg params: String?): Int {
        var inputStream: InputStream? = null
        var savedFile: RandomAccessFile? = null
        var file: File? = null
        try {
            var downloadedLength = 0L
            val downloadUrl = params[0] ?: return TYPE_FAILED
            val fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"))
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path

            file = File(directory, fileName)

            if (file.exists()) {
                downloadedLength = file.length()
            }

            val contentLength: Long = getContentLength(downloadUrl)

            if (contentLength == 0L) {
                return TYPE_FAILED
            } else if (contentLength == downloadedLength) {
                return  TYPE_SUCCESS
            }

            val client = OkHttpClient()
            val request = Request.Builder()
                    .addHeader("RANGE", "bytes=$downloadedLength-")
                    .url(downloadUrl!!)
                    .build()

            val response = client.newCall(request).execute()

            inputStream = response.body()?.byteStream()
            savedFile = RandomAccessFile(file, "rw")
            savedFile.seek(downloadedLength)

            var total = 0
            val b = ByteArray(1024)

            while (true) {
                val len = inputStream?.read(b)
                if (len == null || len == -1) {
                    break
                }

                if (isCanceled) {
                    return TYPE_CANCELED
                } else if(isPaused) {
                    return  TYPE_PAUSED
                } else {
                    total += len

                    savedFile.write(b, 0, len)

                    val progress = (total + downloadedLength) * 100 / contentLength

                    publishProgress(progress.toInt())
                }
            }

            response.body()?.close()
            return TYPE_SUCCESS
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close()
                }

                if (savedFile != null) {
                    savedFile.close()
                }

                if (isCanceled && file != null) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return TYPE_FAILED
    }


    override fun onProgressUpdate(vararg values: Int?) {
        val progress = values[0] ?: return
        if (progress > lastProgress) {
            listener.onProgress(progress)
            lastProgress = progress
        }
    }

    override fun onPostExecute(result: Int?) {
        when (result) {
            TYPE_SUCCESS -> {
                listener.onSuccess()
            }
            TYPE_FAILED -> {
                listener.onFailed()
            }
            TYPE_PAUSED -> {
                listener.onPaused()
            }
            TYPE_CANCELED -> {
                listener.onCanceled()
            }
        }
    }


    fun pauseDownload() {
        isPaused = true
    }

    fun cancelDownload() {
        isCanceled = true
    }

    private fun getContentLength(downloadUrl: String): Long {
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(downloadUrl)
                .build()

        val response = client.newCall(request).execute()
        if (response != null && response.isSuccessful) {
            val contentLength = response.body()?.contentLength()
            response.body()?.close()
            return contentLength ?: 0
        }
        return 0
    }
    companion object {
        const val TYPE_SUCCESS = 0
        const val TYPE_FAILED = 1
        const val TYPE_PAUSED = 2
        const val TYPE_CANCELED = 3
    }
}