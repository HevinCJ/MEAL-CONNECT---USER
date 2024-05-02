package com.example.mealconnectuser.adapter

import android.content.Intent
import android.net.Uri
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.mealconnectuser.R
import com.example.mealconnectuser.utils.LocationData
import com.example.mealconnectuser.viewModel.MainViewModel

class BindingAdapter {


    companion object{


        @BindingAdapter("android:convertUrlToImage")
        @JvmStatic
        fun convertUrlToImage(view:ImageView,url:String){
            Glide.with(view.context)
                .load(url)
                .centerCrop()
                .into(view)
        }

        @BindingAdapter("android:IntentToCallerApp")
        @JvmStatic
        fun IntentToCallerApp(view: ImageButton,phoneno:String){
            view.setOnClickListener {
                if (phoneno.isNotEmpty()) {
                    val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneno"))
                    view.context.startActivity(callIntent)
                }
            }
        }


        @BindingAdapter("android:IntentToMaps")
        @JvmStatic
        fun IntentToMaps(view: ImageButton,location:LocationData){
            view.setOnClickListener {
                val query = "${location.latitude},${location.longitude}"
                val mapIntent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("geo:0,0?q=$query")
                }
                if (query.isNotEmpty()){
                    view.context.startActivity(mapIntent)
                }
            }
        }


        @BindingAdapter("android:checkWhetherZeroOrNot")
        @JvmStatic
        fun checkWhetherZeroOrNot(textView: TextView,amount:String){
            val newAmount = amount.toInt()
            if (newAmount == 0) {
                textView.setText((R.string.free_text))
            }else{
                textView.text = "$amount Rs"
            }

        }
        @BindingAdapter("userQuantityOrDefault")
        @JvmStatic
        fun userQuantityOrDefault(textView: TextView, userQuantity: String?) {
            if (userQuantity.isNullOrEmpty()){
                textView.text = "1"
            }else{
                textView.text=userQuantity
            }
        }


    }
}