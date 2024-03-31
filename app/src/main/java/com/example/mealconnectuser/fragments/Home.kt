package com.example.mealconnectuser.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealconnectuser.adapter.MealAdapter
import com.example.mealconnectuser.databinding.FragmentHomeBinding
import com.example.mealconnectuser.viewModel.MainViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class Home : Fragment() {
  private var homefrag:FragmentHomeBinding?=null
    private val binding get() = homefrag!!

    private val mainviewmodel:MainViewModel by viewModels()
    private val adapter by lazy { MealAdapter(mainviewmodel) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

       homefrag= FragmentHomeBinding.inflate(layoutInflater,container,false)


        setUpRecyclerView()

        mainviewmodel.getalldata.observe(viewLifecycleOwner){data->
            adapter.setMeal(data)
        }



        return binding.root
    }

    private fun setUpRecyclerView() {
        binding.recyclerviewhome.adapter=adapter
        binding.recyclerviewhome.layoutManager=LinearLayoutManager(requireContext())
    }




}