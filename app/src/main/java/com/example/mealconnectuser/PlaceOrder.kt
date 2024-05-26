package com.example.mealconnectuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealconnectuser.activity.PaymentActivity
import com.example.mealconnectuser.adapter.PlaceOrderAdapter
import com.example.mealconnectuser.databinding.FragmentPlaceOrderBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.utils.PartnerData

class PlaceOrder : Fragment() {
    private var placeorder:FragmentPlaceOrderBinding?=null
    private val binding get() = placeorder!!

    private lateinit var placeOrderAdapter: PlaceOrderAdapter

    private lateinit var preferences: AppPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = AppPreferences(requireContext())


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        placeOrderAdapter = PlaceOrderAdapter()
        placeorder = FragmentPlaceOrderBinding.inflate(layoutInflater,container,false)

        setUsernameOnUi()
        setUpRecyclerView()

        val data = requireActivity().intent.getStringArrayListExtra("key_data")
        val total = requireActivity().intent.getStringExtra("key_sum")
        binding.txtviewamountpayment.text = total
        if (data != null) {
            Log.d("dataSize", data.toString())
            placeOrderAdapter.setPlaceOrderList(data)
        } else {
            Log.d("dataIsNull", "Data is null")
        }
        binding.paymentbtn.setOnClickListener{
            setNavigationFromPlaceOrderToPaymentGateway(data,total)

        }

        Log.d("totalsum", total.toString())


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

    private fun setNavigationFromPlaceOrderToPaymentGateway(data: ArrayList<String>?,total:String?) {
        val partnerDataList = mutableListOf<PartnerData>()

        if (data != null) {
            partnerDataList.addAll(data.chunked(6).map {
                PartnerData(
                    it[0], // key
                    it[1], // amount
                    it[2], // userquantity
                    it[3], // image
                    it[4], // descp
                    it[5]  // mealname
                )
            })

            if (partnerDataList.isNotEmpty()) {
                val intent = Intent(requireActivity(), PaymentActivity::class.java)
                intent.putExtra("total_amount", total.toString())
                Log.d("total_amount_data",total.toString())
                intent.putExtra("order_key",partnerDataList[0].key)
                Log.d("partner_key_data",partnerDataList[0].key)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "Partner data list is empty", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Data is null", Toast.LENGTH_SHORT).show()
        }
    }



}