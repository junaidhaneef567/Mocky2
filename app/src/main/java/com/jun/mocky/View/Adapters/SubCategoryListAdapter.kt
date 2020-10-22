package com.jun.mocky.View.Adapters

import android.text.method.TextKeyListener.clear
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jun.mocky.Model.dataModel.SubCategoryResponseModel
import com.jun.mocky.R
import kotlinx.android.synthetic.main.item_list.view.*
import java.util.Collections.addAll

class SubCategoryListAdapter(
    private val subCategory:ArrayList<SubCategoryResponseModel>,
    val itemClickListener:OnSubCategoryClickListener)
    :RecyclerView.Adapter<SubCategoryListAdapter.DataViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))


    override fun getItemCount(): Int = subCategory.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        holder.bind(subCategory.get(position),itemClickListener)
    }

    class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {

        fun bind(subcategory: SubCategoryResponseModel, listener:OnSubCategoryClickListener)
        {
            itemView.apply {
                Glide.with(list_iv).load(subcategory.imgUrl).into(list_iv)
                list_tv.setText(subcategory.name)


                itemView.setOnClickListener{
                    listener.onSubItemClick(subcategory)
                }
            }
        }
    }


    fun addSubCategories(subcategory:List<SubCategoryResponseModel>) {
        this.subCategory.apply {
            clear()
            addAll(subcategory)
        }

    }

    interface OnSubCategoryClickListener
    {
        fun onSubItemClick(item: SubCategoryResponseModel)
    }
}