package com.example.mealconnectuser.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mealconnectuser.R
import com.example.mealconnectuser.databinding.CustomProgressBarBinding

class CustomProgressBar(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {

    private val binding: CustomProgressBarBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = CustomProgressBarBinding.inflate(inflater, this, true)
        hide()
    }

    fun show() {
        visibility = VISIBLE
    }

    fun hide() {
        visibility = GONE
    }

    fun setText(text: String) {
        binding.txtviewprogressbar.text = text
    }
}
