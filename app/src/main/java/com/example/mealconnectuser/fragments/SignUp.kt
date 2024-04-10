package com.example.mealconnectuser.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.databinding.FragmentSignUpBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.preferences.PreferenceHelper
import com.example.mealconnectuser.utils.UserData
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : Fragment() {
    private var signup:FragmentSignUpBinding?=null
    private val binding get() = signup!!

    private lateinit var auth:FirebaseAuth
    private lateinit var datbaseref:DatabaseReference
    private lateinit var preferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=FirebaseAuth.getInstance()
        datbaseref=FirebaseDatabase.getInstance().getReference("Users")
        preferences = AppPreferences(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        signup = FragmentSignUpBinding.inflate(layoutInflater,container,false)

        binding.txtviewloginclicker.setOnClickListener {
            findNavController().navigate(R.id.action_signUp_to_login)
        }

        binding.signupbtn.setOnClickListener {
            val username = binding.edttxtusername.text.toString()
            val email = binding.edttextemail.text.toString()
            val password = binding.edttextpassword.text.toString()
            val confirmpass = binding.edttextpasswordconfirm.text.toString()
            if (password == confirmpass){
                createUserInFirebase(username,email,password)
            }else{
                Toast.makeText(requireContext(),"Password Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun createUserInFirebase(username:String,email:String,password:String) {
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful){
                    preferences.clearAllPreferences()
                   setValueInFirebase(username,email,password)
                    IntentToMainActivity()
                }else{
                    Toast.makeText(requireContext(),it.exception?.message,Toast.LENGTH_SHORT).show()
                    Log.d("exceptionMessage",it.exception.toString())
                }
            }
        }else{
            Toast.makeText(requireContext(),"Please fill fields",Toast.LENGTH_SHORT).show()
        }

    }

    private fun setValueInFirebase(username: String, email: String, password: String) {
        val user = UserData(username,email,password)
        datbaseref.child(auth.currentUser.uid).setValue(user)
    }

    private fun IntentToMainActivity() {
        if (preferences.profileimage.isNullOrEmpty() || preferences.phoneno.isNullOrEmpty()){
            findNavController().navigate(R.id.action_signUp_to_profile2)
        }else{
            val intent = Intent(requireActivity(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

    }


}