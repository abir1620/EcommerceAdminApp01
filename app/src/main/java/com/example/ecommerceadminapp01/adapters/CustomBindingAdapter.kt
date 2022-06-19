package com.example.ecommerceadminapp01.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.ecommerceadminapp01.utils.getFormattedDate

@BindingAdapter("app:setImageUrl")
fun setImageUrl(imageView: ImageView, url: String?){
    url?.let{
        Glide.with(imageView.context).load(url).into(imageView)
    }
}

@BindingAdapter("app:setDateTime")
fun setDateTime(tv: TextView, dt: Long) {
    tv.text = getFormattedDate(dt, "EEE HH:mm")
}