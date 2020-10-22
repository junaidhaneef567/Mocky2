package com.jun.mocky.Model.Networking

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object RetrofitClient{

    private const val BASE_URL = "http://www.mocky.io/v2/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val api:Api= getRetrofit().create(Api::class.java)

}