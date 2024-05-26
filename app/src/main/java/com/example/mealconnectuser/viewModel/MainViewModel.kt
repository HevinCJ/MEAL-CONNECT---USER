package com.example.mealconnectuser.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mealconnectuser.utils.NetworkResult
import com.example.mealconnectuser.utils.PartnerData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.launch

class MainViewModel(application:Application):AndroidViewModel(application) {

    private var partnerRef:DatabaseReference
    var getAllMeals = MutableLiveData<List<PartnerData>>()
    var getAllCartItems = MutableLiveData<List<PartnerData>>()
     var getAlltotalSum = MutableLiveData<Double>()


    init {
        partnerRef=FirebaseDatabase.getInstance().getReference("Partner")
        getAllCartItems()
        calculateTotalPriceOfItems()
    }

    fun getAllDataFromFirebase(resultCallback:(NetworkResult<List<PartnerData>>) -> Unit){

        resultCallback(NetworkResult.Loading())

        partnerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userlist = mutableListOf<PartnerData>()
                for (userSnapshot in snapshot.children) {
                    for (partnerSnapshot in userSnapshot.children) {
                        val partnerData = partnerSnapshot.getValue<PartnerData>()
                        partnerData?.let {
                            userlist.add(it)
                            
                        }
                    }
                }

                resultCallback(NetworkResult.Success(userlist))
                Log.d("userlist", userlist.toString())
            }

            override fun onCancelled(error: DatabaseError) {
               resultCallback(NetworkResult.Error(null,error.message))
            }

        })
    }

    fun addToCartOperation(addedToCart:Boolean,partnerData: PartnerData){
        viewModelScope.launch {
            when(addedToCart){
                true-> partnerRef.child(partnerData.id).child(partnerData.key).child("addedToCart").setValue(true)
                false-> partnerRef.child(partnerData.id).child(partnerData.key).child("addedToCart").setValue(false)
            }

        }
    }

    fun getAllCartItems() {
        partnerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val cartItems = mutableListOf<PartnerData>()

                for (userSnapshot in snapshot.children) {
                    for (partnerSnapshot in userSnapshot.children) {
                        val partnerData = partnerSnapshot.getValue<PartnerData>()
                        partnerData?.let {
                            if (it.addedToCart) {
                                cartItems.add(it)
                            }
                        }
                    }
                }

                getAllCartItems.value = cartItems
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }




    fun calculateTotalPriceOfItems() {
        partnerRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalSum = 0.0

                for (userSnapshot in dataSnapshot.children) {
                    for (partnerSnapshot in userSnapshot.children) {
                        val partnerData = partnerSnapshot.getValue(PartnerData::class.java)
                        partnerData?.let {
                            if (it.addedToCart) {
                                val itemTotal = try {
                                    it.userquantity.toDouble() * it.amount.toDouble()
                                } catch (e: NumberFormatException) {
                                    0.0 // Handle exception gracefully, default to 0.0
                                }
                                totalSum += itemTotal
                            }
                        }
                    }
                }

                // Update LiveData with the total sum value after calculating it
                getAlltotalSum.value = totalSum

                Log.d("Total sum of items:", totalSum.toString())
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }










}