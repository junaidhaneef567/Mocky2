package com.jun.mocky.Model.dataModel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubCategoryJSONResponse {

    @SerializedName("arrayOfProducts")
    @Expose
    val arrayOfSubProducts: List<SubCategoryResponseModel>? = null
}