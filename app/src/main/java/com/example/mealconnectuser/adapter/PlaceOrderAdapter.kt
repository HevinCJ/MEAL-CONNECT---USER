package com.example.mealconnectuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealconnectuser.databinding.PlaceOrderItemBinding
import com.example.mealconnectuser.utils.PartnerData
import kotlin.collections.ArrayList

class PlaceOrderAdapter(): RecyclerView.Adapter<PlaceOrderAdapter.PlaceOrderViewHolder>() {

    private var placeOrderList = ArrayList<PartnerData>()

    class PlaceOrderViewHolder(private val binding: PlaceOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMealData(mealData: PartnerData) {
            binding.cartItems = mealData
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceOrderViewHolder {
        return PlaceOrderViewHolder(
            PlaceOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return placeOrderList.size
    }

    override fun onBindViewHolder(holder: PlaceOrderViewHolder, position: Int) {
        val currentItem = placeOrderList[position]
        holder.bindMealData(currentItem)
        Log.d("currentmeal",currentItem.toString())
    }

    fun setPlaceOrderList(numbers: ArrayList<String>?) {
        if (numbers != null) {
            val partnerDataList = numbers.chunked(6).map {
                PartnerData(
                    it[0], // key
                    it[1], // amount
                    it[2], // userquantity
                    it[3], // image
                    it[4], // descp
                    it[5]  // mealname
                )
            }
            this.placeOrderList.clear()
            this.placeOrderList.addAll(partnerDataList)
            notifyDataSetChanged()
            Log.d("currentmeal", numbers.toString())
        }
    }




}
