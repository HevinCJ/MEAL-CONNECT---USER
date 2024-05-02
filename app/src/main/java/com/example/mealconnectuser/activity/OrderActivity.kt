package com.example.mealconnectuser.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mealconnectuser.R
import com.example.mealconnectuser.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    private var binding:ActivityOrderBinding?=null
    private var navControllerL:NavController?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.order_nav_graph) as NavHostFragment
        navControllerL = navHostFragment.navController



    }

    override fun onSupportNavigateUp(): Boolean {
        return navControllerL!!.navigateUp()|| super.onSupportNavigateUp()
    }
}