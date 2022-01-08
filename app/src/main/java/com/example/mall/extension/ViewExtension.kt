package com.example.mall.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageSrc")
fun ImageView.setUrlSource(url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(this).load(url).centerCrop().into(this)
    }
}