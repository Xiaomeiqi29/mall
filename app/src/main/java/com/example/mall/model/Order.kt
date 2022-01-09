package com.example.mall.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var sku: String? = null,
    var quantity: Int? = null,
    var price: String? = null,
    var total: String? = null,
    var address: CustomerAddress? = null
) : Parcelable

@Parcelize
data class CustomerAddress(
    var name: String? = null,
    var phone: String? = null,
    var address: String? = null
) : Parcelable