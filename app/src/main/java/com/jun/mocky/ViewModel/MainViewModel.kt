package com.jun.mocky.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.jun.mocky.Model.Repository.MainRepository
import com.jun.mocky.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: MainRepository):ViewModel() {


    fun getCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data= mainRepository.getCategories().arrayOfProducts))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }


    fun getSubCategories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getSubcategories().arrayOfSubProducts))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}