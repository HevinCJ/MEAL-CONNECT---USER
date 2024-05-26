package com.example.mealconnectuser.fragments.cart

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealconnectuser.PlaceOrder
import com.example.mealconnectuser.activity.OrderActivity
import com.example.mealconnectuser.databinding.CustomAlertDialogueBinding
import com.example.mealconnectuser.databinding.FragmentCartBinding
import com.example.mealconnectuser.utils.CustomProgressBar
import com.example.mealconnectuser.utils.PartnerData
import com.example.mealconnectuser.viewModel.MainViewModel

class Cart : Fragment() {
    private var cart:FragmentCartBinding?=null
    private val binding get() = cart!!

  private val mainViewModel:MainViewModel by viewModels()
  private val adapter: CartAdapter by lazy { CartAdapter(mainViewModel) }

    private lateinit var progressBar:CustomProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        cart= FragmentCartBinding.inflate(layoutInflater,container,false)


        progressBar = CustomProgressBar(requireContext(), null)
        binding.root.addView(progressBar)



        setRecyclerView()


        mainViewModel.getAllCartItems.observe(viewLifecycleOwner){
            if (it.isNullOrEmpty()) {
                progressBar.setText("No Items")
                binding.placeorderbtn.isEnabled = false
            }else{
                adapter.setItemsInCart(it)
                binding.placeorderbtn.isEnabled = true
            }
            adapter.setItemsInCart(it)
            Log.d("cartitems",it.toString())
        }

        mainViewModel.getAlltotalSum.observe(viewLifecycleOwner){totalsum->
            binding.txtviewamount.text = totalsum.toString()
        }

        binding.placeorderbtn.setOnClickListener {
            val dialogueBinding = CustomAlertDialogueBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(requireContext())

            builder.setView(dialogueBinding.root)
            val alertDialog = builder.create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.show()

            dialogueBinding.nextbtn.setOnClickListener{
                Log.d("CartFragment", "nextbtn clicked")
                val isChecked = dialogueBinding.checkBox.isChecked
                if (isChecked) {
                    Log.d("CartFragment", "nextbtn intentstartd")
                    IntentToOrderActivity()
                } else {

                }
            }
        }



        return binding.root
    }

    private fun IntentToOrderActivity() {
        val intent = Intent(requireActivity(), OrderActivity::class.java)
        val orderDataList = mainViewModel.getAllCartItems.value

        val numbers = ArrayList<String>()

        orderDataList?.forEach { partnerData ->
            numbers.add(partnerData.key)
            numbers.add(partnerData.amount)
            numbers.add(partnerData.userquantity)
            numbers.add(partnerData.image)
            numbers.add(partnerData.descp)
            numbers.add(partnerData.mealname)
        }
        val totalSum = mainViewModel.getAlltotalSum.value.toString()

        intent.putStringArrayListExtra("key_data", numbers)
        intent.putExtra("key_sum", totalSum)
        Log.d("CartFragment", "totalSum: $totalSum")
        startActivity(intent)
    }




    private fun setRecyclerView() {
        binding.cartrcview.adapter=adapter
        binding.cartrcview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        cart=null
    }


}