package com.example.mealconnectuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth


class SignUp : Fragment() {
    private var signup:FragmentSignUpBinding?=null
    private val binding get() = signup!!

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth=FirebaseAuth.getInstance()
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
            val email = binding.edttextemail.text.toString()
            val password = binding.edttextpassword.text.toString()
            val confirmpass = binding.edttextpasswordconfirm.text.toString()
            if (password == confirmpass){
                createUserInFirebase(email,password)
            }else{
                Toast.makeText(requireContext(),"Password Wrong",Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun createUserInFirebase(email:String,password:String) {
        if (email.isNotEmpty() && password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                if (it.isSuccessful){
                    IntentToMainActivity()
                }else{
                    Toast.makeText(requireContext(),it.exception?.message,Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(requireContext(),"Please fill fields",Toast.LENGTH_SHORT).show()
        }

    }

    private fun IntentToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }


}