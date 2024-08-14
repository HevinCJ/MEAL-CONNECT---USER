package com.example.mealconnectuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealconnectuser.adapter.MealAdapter
import com.example.mealconnectuser.databinding.FragmentHomeBinding
import com.example.mealconnectuser.utils.CustomProgressBar
import com.example.mealconnectuser.utils.NetworkResult
import com.example.mealconnectuser.viewmodel.HomeViewModel

import com.example.mealconnectuser.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Home : Fragment() {
  private var homefrag:FragmentHomeBinding?=null
    private val binding get() = homefrag!!

    private val homeViewModel:HomeViewModel by viewModels()
        private val mainviewmodel:MainViewModel by viewModels()

    private val adapter by lazy { MealAdapter(mainviewmodel) }

    private lateinit var progressBar: CustomProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homefrag = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        progressBar = CustomProgressBar(requireContext(), null)
        binding.root.addView(progressBar)

        setUpRecyclerView()

        homeViewModel.getAllPartnerDataFromFirebase()
        homeViewModel.partnerLiveData.observe(viewLifecycleOwner){result->
            when(result){
                is NetworkResult.Error -> {
                    progressBar.show()
                    progressBar.setText("Something Went Wrong,Please try again later...")
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
                is NetworkResult.Nodata -> {
                    progressBar.hide()
                    handleCartEmptyState(true)
                }
                is NetworkResult.Success -> {
                    progressBar.hide()
                    handleCartEmptyState(false)
                    result.data?.let { adapter.setMeal(it) }
                }
            }
        }






    }
    private fun handleCartEmptyState(isEmpty: Boolean) {
        if (isEmpty && homefrag!=null) {
            binding.imgviewnodata.visibility = View.VISIBLE
            binding.txtviewnodata.visibility = View.VISIBLE
        } else {
            binding.imgviewnodata.visibility = View.INVISIBLE
            binding.txtviewnodata.visibility = View.INVISIBLE
        }
    }


    private fun setUpRecyclerView() {
        binding.recyclerviewhome.adapter=adapter
        binding.recyclerviewhome.layoutManager=LinearLayoutManager(requireContext())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        homefrag=null
    }

}