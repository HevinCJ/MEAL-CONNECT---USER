package com.example.mealconnectuser.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.databinding.ActivityMainBinding
import com.example.mealconnectuser.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var navController:NavController

    private val viewmodel:MainViewModel by viewModels()

    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController=navHostFragment.navController


        binding?.btmnavigationbar?.setupWithNavController(navController)

        binding?.btmnavigationbar?.setOnItemSelectedListener {
            when(it.itemId){
                R.id.homem ->{
                    navController.navigate(R.id.home2)
                    true
                }
                R.id.cartm ->{
                    navController.navigate(R.id.cart)
                    true
                }
                R.id.profilem ->{
                    navController.navigate(R.id.profile)
                    true
                }

                else -> {
                    navController.navigate(R.id.home2)
                    true
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()||super.onSupportNavigateUp()
    }
}