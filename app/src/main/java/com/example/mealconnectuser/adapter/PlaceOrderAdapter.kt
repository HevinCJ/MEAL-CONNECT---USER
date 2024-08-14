package com.example.mealconnectuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.databinding.PlaceOrderItemBinding
import com.example.mealconnectuser.utils.PartnerData

class PlaceOrderAdapter(): RecyclerView.Adapter<PlaceOrderAdapter.PlaceOrderViewHolder>() {

    private var placeOrderList:List<PartnerData>  = emptyList()

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

    fun setPlaceOrderList(partnerDataList: Array<PartnerData>) {
        if (partnerDataList != null) {
            Log.d("PlaceOrderAdapter", partnerDataList[0].mealname.toString())
            this.placeOrderList= partnerDataList.toList()
            notifyDataSetChanged()

        }
    }




}
