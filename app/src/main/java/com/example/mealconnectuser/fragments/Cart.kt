package com.example.mealconnectuser.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealconnectuser.R
import com.example.mealconnectuser.adapter.CartAdapter
import com.example.mealconnectuser.databinding.FragmentCartBinding
import com.example.mealconnectuser.viewModel.MainViewModel

class Cart : Fragment() {
    private var cart:FragmentCartBinding?=null
    private val binding get() = cart!!

  private val mainViewModel:MainViewModel by viewModels()
    private val cartAdapter:CartAdapter by lazy { CartAdapter(mainViewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cart= FragmentCartBinding.inflate(layoutInflater,container,false)

        setRecyclerView()

        mainViewModel.getallMeal.observe(viewLifecycleOwner){item->
            cartAdapter.setCartItem(item)
        }

        mainViewModel.getTotalAmount.observe(viewLifecycleOwner){amount->
            if (amount!=null){
                binding.txtviewamount.text = amount.toString()
            }else{
                binding.txtviewamount.text = "0.0"
            }

        }



        return binding.root
    }



    private fun setRecyclerView() {
        binding.cartrcview.adapter=cartAdapter
        binding.cartrcview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }



    override fun onDestroy() {
        super.onDestroy()
        cart=null
    }

}