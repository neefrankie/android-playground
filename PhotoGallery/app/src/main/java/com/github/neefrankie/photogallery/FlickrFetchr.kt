package com.github.neefrankie.photogallery

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.io.IOException


class FlickrFetchr : AnkoLogger {

    fun fetchItems(): List<String> {
        return try {
            val jsonString = Fetch().get("https://dog.ceo/api/breeds/image/random/3").end().body()?.string() ?: return listOf()
            val gallery = gson.fromJson(jsonString, GalleryItem::class.java)

            gallery.message
        } catch (e: IOException) {
            info("Failed to fetch items $e")

            listOf()
        }
    }
}