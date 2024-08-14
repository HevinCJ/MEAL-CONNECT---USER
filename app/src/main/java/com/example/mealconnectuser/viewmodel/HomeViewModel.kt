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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseAuth:FirebaseAuth):ViewModel() {

    private var partnerRef: DatabaseReference

    init {
        partnerRef= FirebaseDatabase.getInstance().getReference("Partner")
    }

    private var _partnerItemsLiveData = MutableLiveData<NetworkResult<List<PartnerData>>>()
    val partnerLiveData: LiveData<NetworkResult<List<PartnerData>>> = _partnerItemsLiveData





    fun getAllPartnerDataFromFirebase() {
        viewModelScope.launch{
            _partnerItemsLiveData.value = NetworkResult.Loading()

            partnerRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userList = mutableListOf<PartnerData>()
                    for (userSnapshot in snapshot.children) {
                        for (partnerSnapshot in userSnapshot.children) {
                            val partnerData = partnerSnapshot.getValue(PartnerData::class.java)
                            partnerData?.let {
                                userList.add(it)
                            }
                        }
                    }
                    if (userList.isEmpty()) {
                        _partnerItemsLiveData.value = NetworkResult.Nodata(userList)
                    } else {
                        _partnerItemsLiveData.value = NetworkResult.Success(userList)
                    }

                    Log.d("userList", userList.size.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    _partnerItemsLiveData.value = NetworkResult.Error(null,error.message)
                }
            })
        }
    }
}