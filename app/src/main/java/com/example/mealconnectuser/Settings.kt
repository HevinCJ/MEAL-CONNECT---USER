package com.example.mealconnectuser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.mealconnectuser.activity.MainActivity
import com.example.mealconnectuser.activity.StartActivity
import com.example.mealconnectuser.databinding.FragmentSettingsBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.google.firebase.auth.FirebaseAuth

class Settings : Fragment() {
    private var settings:FragmentSettingsBinding?=null
    private val binding get() = settings!!

    private lateinit var preferences: AppPreferences
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = AppPreferences(requireContext())
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        settings = FragmentSettingsBinding.inflate(layoutInflater,container,false)

        setTheProfile()

        binding.logoutbtn.setOnClickListener {
            if (auth.currentUser!=null){
                auth.signOut()
                preferences.clearAllPreferences()
                intentToStartActivity()
            }
        }




        return binding.root
    }

    private fun setTheProfile() {
        Glide.with(requireContext()).load(preferences.profileimage).into(binding.imagviewProfile)
        binding.txtviewemail.text = preferences.email
        binding.txtviewname.text = preferences.username
        binding.txtviewphoneno.text = preferences.phoneno
    }


    private fun intentToStartActivity() {
            val intent = Intent(requireActivity(), StartActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
    }

}