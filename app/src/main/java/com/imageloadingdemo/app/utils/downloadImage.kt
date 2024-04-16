package com.imageloadingdemo.app.utils


import com.imageloadingdemo.app.model.UnSplashImageDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL


suspend fun downloadImage(imageUrl: List<UnSplashImageDataItem>): ByteArray? {
    imageUrl.map {
        val url = URL(it.urls.regular)
        val connection = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection
        connection.doInput = true
        withContext(Dispatchers.IO) {
            connection.connect()
        }
        return if (connection.responseCode == HttpURLConnection.HTTP_OK) {
            connection.inputStream.use { it.readBytes() }
        } else {
            null
        }
    }
    return null
}