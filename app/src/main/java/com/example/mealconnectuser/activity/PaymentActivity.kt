package com.example.mealconnectuser.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealconnectuser.databinding.ActivityPaymentBinding
import com.example.mealconnectuser.preferences.AppPreferences
import com.example.mealconnectuser.utils.PartnerData
import com.google.android.gms.common.util.Strings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject


class PaymentActivity : AppCompatActivity(),PaymentResultWithDataListener {
    private var binding:ActivityPaymentBinding ?=null

    private lateinit var preferences: AppPreferences
    private lateinit var partnerData: PartnerData

    private lateinit var item: Array<PartnerData>


    private lateinit var databaseref:DatabaseReference
    private lateinit var auth:FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        auth = FirebaseAuth.getInstance()
        databaseref = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)
        preferences = AppPreferences(this)



        val amount = intent.getStringExtra("total_amount")?.toDouble()
        Log.d("amount_payment_gateway",amount.toString())


        val json = intent.getStringExtra("order_item")
        val gson = Gson()
         item = gson.fromJson(json, Array<PartnerData>::class.java)
        Log.d("amount_payment_item", item.size.toString())



        Checkout.preload(this)

        // Initialize Razorpay with the correct API key 
        val co = Checkout()
        co.setKeyID("rzp_test_PX371cTQrQ0FK6") // Use your Razorpay test key

        startPayment(amount,item)


    }


    private fun startPayment(amount:Double?,item:Array<PartnerData>?) {

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

        for (partnerData in item) {
            databaseref.child("foodItems").child(partnerData.key).child("isorderplaced").setValue(true).addOnCompleteListener{
                if (it.isSuccessful){
                    databaseref.child("foodItems").child(partnerData.key).child("userPhoneNo").setValue(preferences.phoneno)
                    intentToMainActivity()
                }
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

    private fun intentToMainActivity(){
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}