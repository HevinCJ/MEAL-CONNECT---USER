package com.example.mealconnectuser.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mealconnectuser.repository.MealRepository
import com.example.mealconnectuser.room.MealDatabase
import com.example.mealconnectuser.room.MealEntity
import com.example.mealconnectuser.utils.PartnerData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application:Application):AndroidViewModel(application) {

    private var databaseref:DatabaseReference
    var getalldata = MutableLiveData<List<PartnerData>>()
    var getallMeal:LiveData<List<MealEntity>>
    var getTotalAmount:LiveData<Double>

    private val mealdao=MealDatabase.getInstance(application).mealDao()
    private val mealRepository:MealRepository

    init {

        databaseref=FirebaseDatabase.getInstance().getReference("Partner")
        getAllDataFromFirebase()
        mealRepository= MealRepository(mealdao)
        getallMeal=mealdao.getallMeal()
        getTotalAmount=mealdao.getTotalAmount()
    }

    fun getAllDataFromFirebase(){
        databaseref.addValueEventListener(object : ValueEventListener {
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
                getalldata.value = userlist
                Log.d("userlist", userlist.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertMeal(meal: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            mealRepository.insertMeal(meal)
        }
    }

    fun deleteMeal(meal: MealEntity){
        viewModelScope.launch(Dispatchers.IO){
            mealRepository.deleteMeal(meal)
        }
    }












}