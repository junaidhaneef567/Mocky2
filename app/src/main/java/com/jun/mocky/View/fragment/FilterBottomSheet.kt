package com.jun.mocky.View.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.jun.mocky.R
import kotlinx.android.synthetic.main.fragment_filter_bottom_sheet.view.*
import java.lang.ClassCastException
import kotlin.apply

/**
 * A simple [Fragment] subclass.
 */
class FilterBottomSheet : BottomSheetDialogFragment() {

    private var listener: Range?=null
    var price: String? = null
    var quantity:String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v=  inflater.inflate(R.layout.fragment_filter_bottom_sheet, container, false)

        v.apply.setOnClickListener {

            price=v.price_range.text.toString()
            quantity=v.quantity.text.toString()
            listener!!.onItemSent(price!!,quantity!!)
            dismiss()
        }


        return v
    }


    interface Range {
        fun onItemSent(priceRange: String,quantity:String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try{
            listener=context as Range?
        }catch (e: ClassCastException)
        {
            throw ClassCastException(context.toString())
        }
    }

}
