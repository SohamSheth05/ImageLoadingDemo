package com.imageloadingdemo.app.api


import com.imageloadingdemo.app.model.UnSplashImageDataItem
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {


    @GET("photos")
    suspend fun loadImage(@QueryMap hashMap: MutableMap<String, Any>): List<UnSplashImageDataItem>
}