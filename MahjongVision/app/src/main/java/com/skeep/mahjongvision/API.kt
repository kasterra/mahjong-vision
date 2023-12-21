package com.skeep.mahjongvision

import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface API {
    @Multipart
    @POST("/image_to_data")
    fun imageToData(
        @Part imageFile: MultipartBody.Part
    ): Call<String>


    @Headers("Content-Type: application/json")
    @POST("/calculate")
    fun calculate(
        @Body body:String
    ): Call<String>
}