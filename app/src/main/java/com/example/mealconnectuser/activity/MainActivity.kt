package com.example.mealconnectuser.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mealconnectuser.R
import com.example.mealconnectuser.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.btmnavigationbar.setupWithNavController(navController)

        binding.btmnavigationbar.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homem -> {
                    navController.navigate(R.id.home)
                }
                R.id.cartm -> {
                    navController.navigate(R.id.cart)
                }
                R.id.settingsm -> {
                    navController.navigate(R.id.settings)
                }
            }
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}