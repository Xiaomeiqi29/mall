package com.example.mall.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Good(
    val sku: String? = null,
    val title: String? = null,
    val image: String? = null,
    val description: String? = null,
    val price: String? = null
) : Parcelable