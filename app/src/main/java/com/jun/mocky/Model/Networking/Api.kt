package com.jun.mocky.Model.Networking

import com.jun.mocky.Model.dataModel.CategoryJSONResponse
import com.jun.mocky.Model.dataModel.SubCategoryJSONResponse
import retrofit2.http.GET

interface Api {

    @GET("5e16d5263000002a00d5616c")
    suspend fun getCategories(): CategoryJSONResponse //api call for categories

    @GET("5e16d5443000004e00d5616d")
    suspend fun getSubCategories(): SubCategoryJSONResponse // api call for sub categories
}