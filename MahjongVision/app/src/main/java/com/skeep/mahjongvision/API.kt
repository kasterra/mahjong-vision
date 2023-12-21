package com.skeep.mahjongvision

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part



interface API {
    @Multipart
    @POST("/image_to_data")
    fun imageToData(
        @Part imageFile: MultipartBody.Part
    ): Call<String>
    @GET("/calculate")
    fun calculateYaku()
}