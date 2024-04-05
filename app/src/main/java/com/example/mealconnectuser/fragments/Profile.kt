package com.example.mealconnectuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.activity.StartActivity
import com.example.mealconnectuser.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class Profile : Fragment() {

    private var profile:FragmentProfileBinding?=null
    private val binding get() = profile!!

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth= FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profile= FragmentProfileBinding.inflate(layoutInflater,container,false)

        binding.logoutbtn.setOnClickListener {
            if (auth.currentUser!=null){
                auth.signOut()
                intentToMainActivity()
            }
        }

        return binding.root
    }

    private fun intentToMainActivity() {
        val intent = Intent(requireActivity(), StartActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }


}