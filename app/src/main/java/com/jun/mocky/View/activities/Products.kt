package com.jun.mocky.View.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jun.mocky.Model.Networking.ApiHelper
import com.jun.mocky.Model.Networking.RetrofitClient
import com.jun.mocky.Model.dataModel.CategoryResponse
import com.jun.mocky.Model.dataModel.SubCategoryResponseModel
import com.jun.mocky.R
import com.jun.mocky.View.Adapters.CategoryListAdapter
import com.jun.mocky.View.Adapters.SubCategoryListAdapter
import com.jun.mocky.View.fragment.FilterBottomSheet
import com.jun.mocky.ViewModel.MainViewModel
import com.jun.mocky.ViewModel.ViewModelFactory
import com.jun.mocky.utils.Status
import com.jun.mocky.utils.toast
import kotlinx.android.synthetic.main.activity_products.*

class Products : AppCompatActivity(),SubCategoryListAdapter.OnSubCategoryClickListener,FilterBottomSheet.Range{

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: SubCategoryListAdapter

    var price: String? = null
    var quantity:String? = null
    lateinit var categorylist:List<SubCategoryResponseModel>
    lateinit var newList:ArrayList<SubCategoryResponseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)


        setupViewModel() //view model setup
        setupUI()  //recyclerview setup to view categories in grid
        setupObservers() //observing data



        filter.setOnClickListener {
            val bottomsheet= FilterBottomSheet()
            bottomsheet.setStyle(BottomSheetDialogFragment.STYLE_NORMAL,R.style.CustomBottomSheetDialogTheme)
            bottomsheet.show(supportFragmentManager,"TAG")
        }

    }

    private fun setupViewModel() {

        viewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitClient.api))
        ).get(MainViewModel::class.java)

    }


    private fun setupUI() {

        product_rv.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        adapter= SubCategoryListAdapter(arrayListOf(),this)
        product_rv.adapter=adapter


    }

    private fun setupObservers() {

        viewModel.getSubCategories().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        product_rv.visibility = View.VISIBLE
                        progressBar_product.visibility = View.GONE
                        Log.d("sucesssss","Sucesss")
                        resource.data?.let {
                            Log.d("dataaaaa", it.toString())
                            categorylist= it
                            retrieveList(categorylist)
                        }
                    }
                    Status.ERROR -> {
                        product_rv.visibility = View.VISIBLE
                        progressBar_product.visibility = View.GONE
                        Log.d("errrr","errrrrrr")
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        Log.d("loadddd","loaaaad")
                        progressBar_product.visibility = View.VISIBLE
                        product_rv.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(category: List<SubCategoryResponseModel>) {
        adapter.apply {
            addSubCategories(category)
            notifyDataSetChanged()
        }
    }

    override fun onSubItemClick(item: SubCategoryResponseModel) {
    }

    override fun onItemSent(priceRange: String, quantity: String) {

        val newlist = ArrayList<SubCategoryResponseModel>()
        newlist.clear()
        if(priceRange.length.equals(0) || quantity.length.equals(0))
        {
            toast("enter both fields")
        }else
        {
            for (i in 0 until categorylist.size) {
                Log.d("llllsss",categorylist.get(i).price)
                Log.d("qqqqq",categorylist.get(i).quantity.toString())
                if ( categorylist.get(i).price.toInt()<=priceRange.toInt() && categorylist.get(i).quantity >= quantity.toInt())
                {
                    Log.d("index",i.toString())
                    newlist.add(categorylist.get(i))
                }
            }
            retrieveList(newlist)
        }
    }
}
