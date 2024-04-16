package com.imageloadingdemo.app.model

import com.google.gson.annotations.SerializedName

data class UnSplashImageDataItem(
    @SerializedName("urls")
    val urls: Urls,
)