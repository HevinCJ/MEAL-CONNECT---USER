package com.example.mealconnectuser.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.mealconnectuser.R
import com.example.mealconnectuser.databinding.CustomProgressBarBinding

class CustomProgressBar(context: Context, attrs: AttributeSet? = null) : RelativeLayout(context, attrs) {

    private val progressBar: ProgressBar
    private val textview:TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_progress_bar, this, true)
        progressBar = findViewById(R.id.customprogress)
        textview = findViewById(R.id.txtviewprogressbar)
        hide()
    }

    fun show() {
      visibility = VISIBLE
    }

    fun hide() {
       visibility = GONE
    }

    fun setText(text:String){
        textview.text = text
    }
}
