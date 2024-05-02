package com.example.mealconnectuser.fragments.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.databinding.CartAdapterBinding
import com.example.mealconnectuser.utils.PartnerData
import com.example.mealconnectuser.viewModel.MainViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartlist:List<PartnerData> = emptyList()
    private var partnerRef = FirebaseDatabase.getInstance().getReference("Partner")

    class CartViewHolder(private val binding: CartAdapterBinding):RecyclerView.ViewHolder(binding.root){

        fun bindMeal(cartItems:PartnerData,mainViewModel: MainViewModel,partnerref: DatabaseReference){
            binding.cartItems=cartItems
            binding.executePendingBindings()
            binding.removecartbtn.setOnClickListener {
                mainViewModel.addToCartOperation(false,cartItems)
                Toast.makeText(binding.root.context,"Removed ${cartItems.mealname}",Toast.LENGTH_SHORT).show()
                updateQuantityInFirebase(cartItems,partnerref,"1")
            }
        }


        fun updateQuantity(cartItems: PartnerData,partnerref:DatabaseReference) {
            var quantity = binding.txtviewquantityno.text.toString().toInt()
            binding.apply {
                imgbtnadd.setOnClickListener {
                    if (quantity<5){
                        quantity += 1
                        txtviewquantityno.text = quantity.toString()
                      updateQuantityInFirebase(cartItems,partnerref,quantity.toString())
                    } else {
                Toast.makeText(
                    itemView.context,
                    "Maximum Quantity",
                    Toast.LENGTH_SHORT
                ).show()
            }

                }
                imgbtnsub.setOnClickListener {
                    if (quantity > 1) {
                        quantity -= 1
                        txtviewquantityno.text = quantity.toString()
                        updateQuantityInFirebase(cartItems, partnerref, quantity.toString())
                    } else {
                        Toast.makeText(
                            itemView.context,
                            "Quantity cannot be less than 1",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        private fun updateQuantityInFirebase(cartItems: PartnerData, partnerref: DatabaseReference,quantity:String) {
            try {
                partnerref.child(cartItems.id).child(cartItems.key).child("userquantity").setValue(quantity)
            }catch (e:Exception){
                Toast.makeText(
                    itemView.context,
                    "Quantity Update Failed",
                    Toast.LENGTH_SHORT
                ).show()
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
        val currentItem = cartlist[position]
        holder.bindMeal(currentItem,mainViewModel,partnerRef)
        holder.updateQuantity(currentItem,partnerRef)

    }

    fun setItemsInCart(Items:List<PartnerData>){
        this.cartlist=Items
        notifyDataSetChanged()
    }
}