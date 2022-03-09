package com.github.juanncode.challenge99minutes.data.service

import com.github.juanncode.challenge99minutes.data.service.models.responseApi.ResponseApi
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesService {
    @GET("place/nearbysearch/json")
    suspend fun getDataFromDirection(
        @Query("location") location:String,
        @Query("key") key:String,
        @Query("radius") radius:String,
    ): ResponseApi
}