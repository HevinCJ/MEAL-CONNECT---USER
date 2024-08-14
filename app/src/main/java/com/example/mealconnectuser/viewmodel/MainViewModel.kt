package com.example.mealconnectuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealconnectuser.utils.NetworkResult
import com.example.mealconnectuser.utils.PartnerData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel():ViewModel() {

    private var partnerRef:DatabaseReference
    private var userRef:DatabaseReference
    private var auth:FirebaseAuth



    private var _cartItemsLiveData = MutableLiveData<NetworkResult<List<PartnerData>>>()
    val cartItemsLiveData:LiveData<NetworkResult<List<PartnerData>>> = _cartItemsLiveData



    init {
        partnerRef=FirebaseDatabase.getInstance().getReference("Partner")
        userRef=FirebaseDatabase.getInstance().getReference("Users")
        auth = FirebaseAuth.getInstance()
    }







    fun addToCartOperation(partnerData: PartnerData, isAddOperation: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            when (isAddOperation) {
               true -> {
                   userRef.child(auth.currentUser?.uid.orEmpty()).child("foodItems")
                       .child(partnerData.key).setValue(partnerData)
               }

                false -> {
                    userRef.child(auth.currentUser?.uid.orEmpty()).child("foodItems")
                        .child(partnerData.key).removeValue()
                }
            }
        }
    }



    fun getAllCartItemsFromFirebase() {

        viewModelScope.launch{

            _cartItemsLiveData.value = NetworkResult.Loading()

            userRef.child(auth.currentUser?.uid.orEmpty()).child("foodItems")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val cartItems = mutableListOf<PartnerData>()
                        for (itemSnapshot in snapshot.children) {
                            val partnerData = itemSnapshot.getValue(PartnerData::class.java)
                            partnerData?.let {
                                cartItems.add(it)
                            }
                        }
                        if (cartItems.isEmpty()) {
                            _cartItemsLiveData.value = NetworkResult.Nodata(cartItems)
                        } else {
                            _cartItemsLiveData.value =NetworkResult.Success(cartItems)
                        }
                        Log.d("cartItems", cartItems.size.toString())
                    }

                    override fun onCancelled(error: DatabaseError) {
                        _cartItemsLiveData.value =NetworkResult.Error(null, error.message)
                    }
                })
        }


    }







    fun calculateTotalPriceOfItems(totalamountcallback:(NetworkResult<Double>)-> Unit) {

        totalamountcallback(NetworkResult.Loading())

        userRef.child(auth.currentUser!!.uid).child("foodItems").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalSum = 0.0
                for (userSnapshot in dataSnapshot.children) {
                    val items = userSnapshot.getValue(PartnerData::class.java)
                    if (items != null) {
                        val itemTotal = try {
                            items.userquantity.toDouble() * items.amount.toDouble()
                        } catch (e: NumberFormatException) {
                            0.0 // Handle exception gracefully, default to 0.0
                        }
                        totalSum += itemTotal
                    }
                }

                // Update LiveData with the total sum value after calculating it
                totalamountcallback(NetworkResult.Success(totalSum))

                Log.d("Total sum of items:", totalSum.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                totalamountcallback(NetworkResult.Error(null,databaseError.message))
            }
        })
    }










}