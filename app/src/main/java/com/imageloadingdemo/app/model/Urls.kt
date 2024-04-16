package com.imageloadingdemo.app.model

import com.google.gson.annotations.SerializedName

data class Urls(

    @SerializedName("regular")
    val regular: String,
    @SerializedName("thumb")
    val thumb: String
)