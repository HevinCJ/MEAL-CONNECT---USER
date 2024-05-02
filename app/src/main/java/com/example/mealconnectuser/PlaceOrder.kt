package com.example.mealconnectuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mealconnectuser.databinding.FragmentPlaceOrderBinding
import com.example.mealconnectuser.preferences.AppPreferences

class PlaceOrder : Fragment() {
    private var placeorder:FragmentPlaceOrderBinding?=null
    private val binding get() = placeorder!!

    private lateinit var preferences: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = AppPreferences(requireContext())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        placeorder = FragmentPlaceOrderBinding.inflate(layoutInflater,container,false)

        setUsernameOnUi()

        val data = requireArguments().getString("orderdata")
        val total = requireArguments().getString("totalsum")
        binding.txtviewamountpayment.text = total
        Log.d("totalsum", total.toString())


     return binding.root
    }

    private fun setUsernameOnUi() {
        if (preferences.username?.isNotEmpty() == true){
            binding.txtviewusername.text = preferences.username
        }

    }


}