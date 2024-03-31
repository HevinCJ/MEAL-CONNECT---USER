package com.example.mealconnectuser.activity

import com.example.mealconnectuser.R
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mealconnectuser.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private var binding:ActivityStartBinding?=null
    private var navController:NavController?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment=supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController=navHostFragment.navController



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController!!.navigateUp() || super.onSupportNavigateUp()
    }
}