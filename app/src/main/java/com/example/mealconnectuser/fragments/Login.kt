package com.example.mealconnectuser.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.databinding.FragmentLoginBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : Fragment() {
    private var login:FragmentLoginBinding?=null
    private val binding get() = login!!

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var preferences:AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance().getReference("Users")
        preferences = AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        login = FragmentLoginBinding.inflate(layoutInflater,container,false)

        binding.txtviewsignup.setOnClickListener {
            findNavController().navigate(R.id.signUp)
        }

        if (auth.currentUser!=null){
            IntentToMainActivity()
        }


        binding.loginbutton.setOnClickListener {
            val email = binding.edttextemail.text.toString()
            val password = binding.edttextpassword.text.toString()
            authenticateUserLogin(email,password)
        }



        return binding.root
    }

    private fun authenticateUserLogin(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful){
                    IntentToMainActivity()
                    Toast.makeText(requireContext(),"Logged In",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(),it.exception.toString(),Toast.LENGTH_SHORT).show()
                    Log.d("exceptionMessage",it.exception.toString())
                }
            }
        }else{
            Toast.makeText(requireContext(),"Please fill fields",Toast.LENGTH_SHORT).show()
        }
    }

    private fun IntentToMainActivity() {
        if (preferences.profileimage.isNullOrEmpty() || preferences.phoneno.isNullOrEmpty()){
            findNavController().navigate(R.id.action_login_to_profile2)
        }else{
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

    }


}