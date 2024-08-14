    package com.example.mealconnectuser.fragments

    import android.app.AlertDialog
    import android.os.Bundle
    import android.util.Log
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import android.widget.Toast
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.navigation.fragment.findNavController
    import androidx.recyclerview.widget.LinearLayoutManager
    import com.example.mealconnectuser.adapter.CartAdapter
    import com.example.mealconnectuser.databinding.CustomAlertDialogueBinding
    import com.example.mealconnectuser.databinding.FragmentCartBinding
    import com.example.mealconnectuser.utils.CustomProgressBar
    import com.example.mealconnectuser.utils.NetworkResult
    import com.example.mealconnectuser.utils.PartnerData
    import com.example.mealconnectuser.viewmodel.MainViewModel

    class Cart : Fragment() {

        private val cart:FragmentCartBinding by lazy {
            FragmentCartBinding.inflate(layoutInflater)
        }
//        private val binding get() = cart!!

      private val mainViewModel:MainViewModel by viewModels()
      private val adapter: CartAdapter by lazy { CartAdapter(mainViewModel) }

        private lateinit var progressBar:CustomProgressBar

        private var cartItems: List<PartnerData> = emptyList()
        private var totalPrice:Double? = 0.0

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {

            return cart.root


        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            progressBar = CustomProgressBar(requireContext(), null)
            cart.root.addView(progressBar)


            setRecyclerView()
            observeCartItems()
            observeTotalPrice()




            cart.placeorderbtn.setOnClickListener {
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
                        val totalamount = totalPrice.toString()
                        val items = cartItems.toTypedArray()
                        val cartData = CartDirections.actionCartToPlaceOrder(items,totalamount)
                        findNavController().navigate(cartData)
                        alertDialog.hide()
                        Log.d("CartFragment", "nextbtn intentstartd")
                    } else {

                    }
                }
            }
        }



        private fun observeTotalPrice() {
            mainViewModel.calculateTotalPriceOfItems {totalsum->
                when(totalsum){
                    is NetworkResult.Error ->{
                        cart.placeorderbtn.isEnabled = false
                        Toast.makeText(cart.root.context, "Error:Price Not Updated", Toast.LENGTH_SHORT).show()

                    }
                    is NetworkResult.Loading -> {
                        cart.placeorderbtn.isEnabled = false
                    }
                    is NetworkResult.Success -> {
                        cart.txtviewamount.text = totalsum.data.toString()
                        totalPrice= totalsum.data
                    }

                    is NetworkResult.Nodata -> {
                        cart.placeorderbtn.isEnabled = false
                        handleCartEmptyState(true)
                    }
                }

            }
        }

        private fun observeCartItems() {
            mainViewModel.getAllCartItemsFromFirebase()
            mainViewModel.cartItemsLiveData.observe(viewLifecycleOwner){result->
                when(result){
                    is NetworkResult.Error ->{
                        progressBar.show()
                        progressBar.setText("Something Went Wrong..")
                        cart.placeorderbtn.isEnabled = false
                    }
                    is NetworkResult.Loading -> {
                        progressBar.show()
                        progressBar.setText("Loading Cart Items,Please Wait..")

                    }
                    is NetworkResult.Success ->{
                            progressBar.hide()
                            adapter.setItemsInCart(result.data ?: emptyList())
                            cartItems = result.data ?: emptyList()
                            cart.placeorderbtn.isEnabled = true
                            handleCartEmptyState(false)
                    }

                    is NetworkResult.Nodata -> {
                        progressBar.hide()
                        cart.placeorderbtn.isEnabled = false
                        handleCartEmptyState(true)
                        adapter.setItemsInCart(emptyList())
                    }
                }
                Log.d("cartitem",result.data?.size.toString())
            }
        }


        private fun setRecyclerView() {
            cart.cartrcview.adapter=adapter
            cart.cartrcview.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        }


        override fun onDestroyView() {
            super.onDestroyView()

            cart.root.removeAllViews()
        }

        private fun handleCartEmptyState(isEmpty: Boolean) {
            if (isEmpty) {
                cart.noitemimage.visibility = View.VISIBLE
                cart.noitemtxtview.visibility = View.VISIBLE
            } else {
                cart.noitemimage.visibility = View.INVISIBLE
                cart.noitemtxtview.visibility = View.INVISIBLE
            }
        }

    }