package com.example.mealconnectuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealconnectuser.activity.PaymentActivity
import com.example.mealconnectuser.adapter.PlaceOrderAdapter
import com.example.mealconnectuser.databinding.FragmentPlaceOrderBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.utils.PartnerData
import com.google.gson.Gson

class PlaceOrder : Fragment() {
    private var placeorder:FragmentPlaceOrderBinding?=null
    private val binding get() = placeorder!!

    private val placeOrderArgs:PlaceOrderArgs by navArgs<PlaceOrderArgs>()
    private val placeOrderAdapter: PlaceOrderAdapter by lazy { PlaceOrderAdapter() }


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
        setUpRecyclerView()

        val gson = Gson()
        val cartItemJson = gson.toJson(placeOrderArgs.cartItem)


        placeOrderAdapter.setPlaceOrderList(placeOrderArgs.cartItem)
        binding.txtviewamountpayment.text = placeOrderArgs.totalPrice

        binding.paymentbtn.setOnClickListener{
            val intenttopayment = Intent(requireActivity(),PaymentActivity::class.java)

            val amount = placeOrderArgs.totalPrice
            val orderItem = placeOrderArgs.cartItem

            intenttopayment.putExtra("total_amount", amount)
            intenttopayment.putExtra("order_item",cartItemJson)

            startActivity(intenttopayment)
        }
     return binding.root
    }



    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = placeOrderAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }

    private fun setUsernameOnUi() {
        if (preferences.username?.isNotEmpty() == true){
            val currentText = preferences.username.toString().trim()
            val newText = "$currentText's"
            binding.txtviewusername.text = newText
        }else{
            binding.txtviewusername.visibility = View.INVISIBLE
        }

    }






}
