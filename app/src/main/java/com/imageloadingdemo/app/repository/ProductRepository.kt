package com.imageloadingdemo.app.repository

import com.imageloadingdemo.app.api.ApiService
import com.imageloadingdemo.app.model.UnSplashImageDataItem
import javax.inject.Inject
import javax.inject.Singleton

interface ProductRepository {


    suspend fun loadImage(
        hashMap: MutableMap<String, Any>
    ): List<UnSplashImageDataItem>
}

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {

    override suspend fun loadImage(hashMap: MutableMap<String, Any>): List<UnSplashImageDataItem> =
        apiService.loadImage(hashMap)

}
