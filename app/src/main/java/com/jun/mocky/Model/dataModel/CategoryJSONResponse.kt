package com.jun.mocky.Model.dataModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class CategoryJSONResponse {

    @SerializedName("arrayOfProducts")
    @Expose
    val arrayOfProducts: List<CategoryResponse>? = null


}