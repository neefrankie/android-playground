package com.github.neefrankie.photogallery

import com.google.gson.Gson
import okhttp3.*
import org.jetbrains.anko.AnkoLogger

val gson = Gson()

class Fetch : AnkoLogger {

    private var method: String = "GET"
    private val headers = Headers.Builder()
    private val reqBuilder = Request.Builder()
    private var reqBody: RequestBody? = null

    private val contentType: MediaType? = MediaType.parse("application/json")
    private var cacheControl: CacheControl? = null

    fun get(url: String): Fetch {
        reqBuilder.url(url)
        return this
    }

    fun post(url: String): Fetch {
        reqBuilder.url(url)
        method = "POST"
        return this
    }

    fun put(url: String): Fetch {
        reqBuilder.url(url)
        method = "PUT"
        return this
    }

    fun patch(url: String): Fetch {
        reqBuilder.url(url)
        method = "PATCH"
        return this
    }

    fun delete(url: String): Fetch {
        reqBuilder.url(url)
        method = "DELETE"
        return this
    }

    fun header(name: String, value: String): Fetch {
        headers.set(name, value)
        return this
    }

    fun setClient(): Fetch {
        headers.set("X-Client-Type", "android")
                .set("X-Client-Version", BuildConfig.VERSION_NAME)
        return this
    }

    fun setUserId(uuid: String): Fetch {
        headers.set("X-User-Id", uuid)

        return this
    }

    fun noCache(): Fetch {
        cacheControl = CacheControl.Builder()
                .noCache()
                .noStore()
                .noTransform()
                .build()

        return this
    }

    fun body(o: Any?): Fetch {
        reqBody = if (o == null) {
            RequestBody.create(null, "")
        } else {
            RequestBody.create(contentType, gson.toJson(o))
        }

        return this
    }


    /**
     * @return Response
     * See execute for thrown errors.
     */
    fun end(): Response {
        reqBuilder.headers(headers.build())

        if (cacheControl != null) {
            reqBuilder.cacheControl(cacheControl!!)
        }

        reqBuilder.method(method, reqBody)

        val request = reqBuilder.build()

        return client.newCall(request).execute()
    }

    companion object {
        private val client = OkHttpClient()
    }
}