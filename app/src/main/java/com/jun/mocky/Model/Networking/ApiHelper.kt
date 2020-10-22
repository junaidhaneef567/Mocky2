package com.jun.mocky.Model.Networking

class ApiHelper(private val api: Api) {

    suspend fun getCategories() = api.getCategories()

    suspend fun getSubcategories() = api.getSubCategories()
}