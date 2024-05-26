package com.example.mealconnectuser.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.mealconnectuser.databinding.ActivityPaymentBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.viewModel.MainViewModel
import com.google.android.gms.common.util.Strings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(),PaymentResultWithDataListener {
    private var binding:ActivityPaymentBinding ?=null

    private lateinit var preferences: AppPreferences

    private lateinit var databaseref:DatabaseReference
    private lateinit var auth:FirebaseAuth

    private lateinit var id:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        auth = FirebaseAuth.getInstance()
        databaseref = FirebaseDatabase.getInstance().getReference("Partner").child(auth.currentUser.uid)

        preferences = AppPreferences(this)



        val amount = intent.getStringExtra("total_amount")?.toDouble()
        Log.d("amount_payment_gateway",amount.toString())
         id = intent.getStringExtra("order_key").toString()
        Log.d("amount_payment_gateway",id.toString())

        Checkout.preload(this)

        // Initialize Razorpay with the correct API key
        val co = Checkout()
        co.setKeyID("rzp_test_PX371cTQrQ0FK6") // Use your Razorpay test key

        startPayment(amount,id)


    }


    private fun startPayment(amount:Double?,id:String?) {

        try {
            val actualamount = amount?.times(100)
            val options = JSONObject()
            options.put("name", "Meal Connect")
            options.put("description", "Uniting For A Hunger Free Tommorow")
            options.put("theme.color", "#FC8B2D")
            options.put("currency", "INR")
            options.put("order_id","") // Replace with the actual order ID
            options.put("amount",actualamount) // Replace with the actual order amount

            val retryObj = JSONObject()
            retryObj.put("enabled", false)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)

            val prefill = JSONObject()
            prefill.put("email", preferences.email)
            prefill.put("contact", preferences.phoneno)
            options.put("prefill", prefill)

            val co = Checkout()
            co.open(this, options)
        } catch (e: Exception) {
            // Handle payment initiation error
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            Log.d("errorrazorpay",e.message.toString())
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        // Handle payment success

        databaseref.child(id).child("isorderplaced").setValue(true).addOnCompleteListener{
            if (it.isSuccessful){
                databaseref.child(id).child("userPhoneNo").setValue(preferences.phoneno)
                Toast.makeText(this, "Payment is successful", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }

    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        // Handle payment failure
        Toast.makeText(this, "Payment Failed: $p1", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        this.finish()
        Log.d("errorrazorpay",p1.toString())
    }



    override fun onDestroy() {
        super.onDestroy()
        Checkout.clearUserData(this)
    }
}