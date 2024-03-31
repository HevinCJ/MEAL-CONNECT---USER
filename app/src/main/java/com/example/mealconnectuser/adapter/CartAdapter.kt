package com.example.mealconnectuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.databinding.CartAdapterBinding
import com.example.mealconnectuser.databinding.FragmentCartBinding
import com.example.mealconnectuser.room.MealEntity
import com.example.mealconnectuser.viewModel.MainViewModel

class CartAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartlist:List<MealEntity> = emptyList()

    class CartViewHolder(private val binding: CartAdapterBinding):RecyclerView.ViewHolder(binding.root) {
        fun bindCartItems(currentitem: MealEntity) {
            binding.userdata=currentitem
            binding.executePendingBindings()
        }

        fun deleteFromCart(currentitem: MealEntity,mainViewModel: MainViewModel){
            binding.removecartbtn.setOnClickListener {
                mainViewModel.deleteMeal(currentitem)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
       return CartViewHolder(CartAdapterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return cartlist.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
       val currentitem = cartlist[position]
        holder.bindCartItems(currentitem)
        holder.deleteFromCart(currentitem,mainViewModel)
    }

    fun setCartItem(item:List<MealEntity>){
        this.cartlist=item
        notifyDataSetChanged()
    }


}