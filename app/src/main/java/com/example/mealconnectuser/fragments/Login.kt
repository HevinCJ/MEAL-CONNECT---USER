package com.example.mealconnectuser.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.databinding.FragmentLoginBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.utils.CustomProgressBar
import com.example.mealconnectuser.utils.NetworkResult
import com.example.mealconnectuser.utils.RegisterValidation
import com.example.mealconnectuser.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class Login : Fragment() {
    private var login:FragmentLoginBinding?=null
    private val binding get() = login!!


    private lateinit var databaseReference: DatabaseReference
    private lateinit var preferences:AppPreferences

    private lateinit var progressBar: CustomProgressBar

    private val loginviewmodel:LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
        preferences = AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        login = FragmentLoginBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        progressBar = CustomProgressBar(requireContext(),null)
        binding.root.addView(progressBar)

        binding.apply {

            txtviewsignup.setOnClickListener {
                findNavController().navigate(R.id.signUp)
            }

            loginbutton.setOnClickListener{
                val email = edttextemail.text.toString().trim()
                val password = edttextpassword.text.toString().trim()
                loginviewmodel.authenticateUserLogin(email,password)

            }


            lifecycleScope.launch {
                loginviewmodel.login.collect{result->
                    when(result){
                        is NetworkResult.Error -> {
                            Toast.makeText(requireContext(),result.message,Toast.LENGTH_SHORT).show()
                            progressBar.hide()
                        }
                        is NetworkResult.Loading -> {
                            progressBar.show()
                        }
                        is NetworkResult.Nodata -> {
                            progressBar.hide()
                        }
                        is NetworkResult.Success -> {
                            Toast.makeText(requireContext(),"Login Successfull",Toast.LENGTH_SHORT).show()
                            progressBar.hide()
                        }
                    }
                }
            }



            lifecycleScope.launch(Dispatchers.IO){
                Log.d("Currentthread",Thread.currentThread().name)
                loginviewmodel.validation.collect{validation->

                    withContext(Dispatchers.Main){
                        if (validation.email is RegisterValidation.Failed){
                            binding.edttextemail.apply {
                                requestFocus()
                                error = validation.email.message
                            }
                        }

                        if (validation.password is RegisterValidation.Failed){
                            binding.edttextpassword.apply {
                                requestFocus()
                                error = validation.password.message
                            }
                        }
                    }
                }
            }
        }
    }



    private fun IntentToMainActivity() {
        if (preferences.profileimage.isNullOrEmpty() || preferences.phoneno.isNullOrEmpty() || preferences.email.isNullOrEmpty()||preferences.username.isNullOrEmpty()){
            findNavController().navigate(R.id.action_login_to_profile2)
        }else{
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

    }


}