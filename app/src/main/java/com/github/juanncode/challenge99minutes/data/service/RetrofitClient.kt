package com.github.juanncode.challenge99minutes.data.service

import com.github.juanncode.challenge99minutes.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: PlacesService = retrofit.create(PlacesService::class.java)
}