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
import com.jun.mocky.Model.dataModel.CategoryResponse
import com.jun.mocky.Model.Networking.ApiHelper
import com.jun.mocky.Model.Networking.RetrofitClient
import com.jun.mocky.View.Adapters.CategoryListAdapter
import com.jun.mocky.R
import com.jun.mocky.ViewModel.MainViewModel
import com.jun.mocky.ViewModel.ViewModelFactory
import com.jun.mocky.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),CategoryListAdapter.OnCategoryClickListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: CategoryListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupViewModel() //view model setup
        setupUI()  //recyclerview setup to view categories in grid
        setupObservers() //observing data

    }

    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitClient.api))
        ).get(MainViewModel::class.java)

    }


    private fun setupUI() {

        categoryList_rv.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter= CategoryListAdapter(arrayListOf(),this)
        categoryList_rv.adapter=adapter


    }

    private fun setupObservers() {

        viewModel.getCategories().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        categoryList_rv.visibility = View.VISIBLE
                        progressBar_category.visibility = View.GONE
                        Log.d("sucesssss","Sucesss")
                        resource.data?.let {
                            Log.d("dataaaaa", it.toString())
                            retrieveList(it)
                        }
                    }
                    Status.ERROR -> {
                        categoryList_rv.visibility = View.VISIBLE
                        progressBar_category.visibility = View.GONE
                        Log.d("errrr","errrrrrr")
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Log.d("loadddd","loaaaad")
                        progressBar_category.visibility = View.VISIBLE
                        categoryList_rv.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(categoryJSONS: List<CategoryResponse>) {
        adapter.apply {
            addCategories(categoryJSONS)
            notifyDataSetChanged()
        }
    }


    override fun onItemClick(item: CategoryResponse) {
        startActivity(Intent(this,SubCategory::class.java))
    }
}
