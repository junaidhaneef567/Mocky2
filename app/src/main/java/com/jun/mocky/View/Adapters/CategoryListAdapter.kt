package com.jun.mocky.View.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jun.mocky.Model.dataModel.CategoryResponse
import com.jun.mocky.R
import kotlinx.android.synthetic.main.category_item.view.*




class CategoryListAdapter(private val category:ArrayList<CategoryResponse>,val itemClickListener:OnCategoryClickListener): RecyclerView.Adapter<CategoryListAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder=
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_item, parent, false))


    override fun getItemCount(): Int = category.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(category.get(position),itemClickListener)

    }

    fun addCategories(categoryJSON:List<CategoryResponse>) {
        this.category.apply {
            clear()
            addAll(categoryJSON)
        }

    }

    class DataViewHolder(itemview:View): RecyclerView.ViewHolder(itemview){

        fun bind(category: CategoryResponse, listner:OnCategoryClickListener) {
            itemView.apply {
                Glide.with(imageView.context)
                    .load(category.imgUrl)
                    .into(imageView)
                textView.setText(category.name)

                itemView.setOnClickListener{
                    listner.onItemClick(category)
                }
            }
        }
    }

    interface OnCategoryClickListener
    {
        fun onItemClick(item: CategoryResponse)
    }
}