package com.jun.mocky.Model.Repository

import com.jun.mocky.Model.Networking.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getCategories() = apiHelper.getCategories()

    suspend fun getSubcategories() = apiHelper.getSubcategories()
}