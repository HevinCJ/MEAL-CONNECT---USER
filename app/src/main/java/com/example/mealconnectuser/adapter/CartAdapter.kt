package com.example.mealconnectuser.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.databinding.CartAdapterBinding
import com.example.mealconnectuser.utils.PartnerData
import com.example.mealconnectuser.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CartAdapter(private val mainViewModel: MainViewModel): RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var cartlist:List<PartnerData> = emptyList()
    private var userRef = FirebaseDatabase.getInstance().getReference("Users")
    private var auth = FirebaseAuth.getInstance()
    class CartViewHolder(private val binding: CartAdapterBinding):RecyclerView.ViewHolder(binding.root){

        fun bindMeal(cartItems: PartnerData, mainViewModel: MainViewModel, userRef: DatabaseReference, auth: FirebaseAuth) {
            binding.cartItems = cartItems
            binding.executePendingBindings()

            binding.removecartbtn.setOnClickListener {
                mainViewModel.addToCartOperation(cartItems, false)
                Toast.makeText(binding.root.context, "Removed ${cartItems.mealname}", Toast.LENGTH_SHORT).show()
            }

            updateQuantity(cartItems, userRef, auth)
        }


        fun updateQuantity(cartItems: PartnerData,userref: DatabaseReference,auth: FirebaseAuth) {
            var quantity = binding.txtviewquantityno.text.toString().toInt()
            binding.apply {
                imgbtnadd.setOnClickListener {
                    if (quantity<5){
                        quantity += 1
                        txtviewquantityno.text = quantity.toString()
                      updateQuantityInFirebase(auth,cartItems,userref,quantity.toString())
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
                        updateQuantityInFirebase(auth,cartItems, userref, quantity.toString())
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

        private fun updateQuantityInFirebase(auth:FirebaseAuth,cartItems: PartnerData, partnerref: DatabaseReference,quantity:String) {
            try {
                partnerref.child(auth.currentUser!!.uid).child("foodItems").child(cartItems.key).child("userquantity").setValue(quantity)
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
        Log.d("cartitems", cartlist.size.toString())
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
            val currentItem = cartlist[position]
                Log.d("foodItem", currentItem.mealname)
                holder.bindMeal(currentItem, mainViewModel, userRef,auth)
    }
    fun setItemsInCart(Items:List<PartnerData>){
        this.cartlist=Items
        notifyDataSetChanged()
    }

}