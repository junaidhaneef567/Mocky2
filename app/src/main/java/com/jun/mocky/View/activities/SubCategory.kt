package com.jun.mocky.View.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jun.mocky.Model.Networking.ApiHelper
import com.jun.mocky.Model.Networking.RetrofitClient
import com.jun.mocky.Model.dataModel.CategoryResponse
import com.jun.mocky.Model.dataModel.SubCategoryResponseModel
import com.jun.mocky.R
import com.jun.mocky.View.Adapters.CategoryListAdapter
import com.jun.mocky.View.Adapters.SubCategoryListAdapter
import com.jun.mocky.ViewModel.MainViewModel
import com.jun.mocky.ViewModel.ViewModelFactory
import com.jun.mocky.utils.Status
import com.jun.mocky.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sub_category.*

class SubCategory : AppCompatActivity(),SubCategoryListAdapter.OnSubCategoryClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SubCategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)


        setupViewModel() //view model setup
        setupUI()  //recyclerview setup to view subcategories in grid
        setupObservers() //observing data
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitClient.api))
        ).get(MainViewModel::class.java)
    }

    private fun setupUI() {
        sub_categoryList_rv.layoutManager= StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        adapter= SubCategoryListAdapter(arrayListOf(),this)
        sub_categoryList_rv.adapter=adapter
    }


    private fun setupObservers() {
        viewModel.getSubCategories().observe(this, Observer {
            it?.let {resource->
                when(resource.status){
                    Status.SUCCESS ->{
                        sub_categoryList_rv.visibility = View.VISIBLE
                        category_sub_progressBar.visibility = View.GONE
                        resource.data?.let {
                            retrieveList(it)
                        }
                    }
                    Status.ERROR -> {
                        sub_categoryList_rv.visibility = View.VISIBLE
                        category_sub_progressBar.visibility = View.GONE
                        Log.d("errrr","errrrrrr")
                        toast(it.message!!)
                    }
                    Status.LOADING -> {
                        Log.d("loadddd","loaaaad")
                        category_sub_progressBar.visibility = View.VISIBLE
                        sub_categoryList_rv.visibility = View.GONE
                    }
                }
            }
        })
    }


    private fun retrieveList(categoryJSONS: List<SubCategoryResponseModel>) {
        adapter.apply {
            addSubCategories(categoryJSONS)
            notifyDataSetChanged()
        }
    }

    override fun onSubItemClick(item: SubCategoryResponseModel) {
        startActivity(
            Intent(this,Products::class.java)
        )
    }
}
