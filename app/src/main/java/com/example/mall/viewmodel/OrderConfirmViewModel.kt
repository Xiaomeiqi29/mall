package com.example.mall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mall.model.CustomerAddress
import com.example.mall.model.Good
import com.example.mall.model.Order
import com.example.mall.util.RegexUtil

class OrderConfirmViewModel : ViewModel() {
    companion object {
        private const val QUANTITY_DEFAULT = 0
        private const val QUANTITY_MAXIMUM_LIMIT = 10
        private const val QUANTITY_MINIMUM_LIMIT = 1
        private const val QUANTITY_INDEX = 1
    }

    private val _good = MutableLiveData(Good())
    private val _goodQuantity = MutableLiveData(1)
    private val _addClickable = MutableLiveData(true)
    private val _reduceClickable = MutableLiveData(true)
    private val _totalPrice = MutableLiveData("")
    val _name = MutableLiveData("")
    val _phone = MutableLiveData("")
    val _addressName = MutableLiveData("")
    private val _orderStatus = MutableLiveData("")

    val good: LiveData<Good> = _good
    val goodQuantity: LiveData<Int> = _goodQuantity
    val addClickable: LiveData<Boolean> = _addClickable
    val reduceClickable: LiveData<Boolean> = _reduceClickable
    val totalPrice: LiveData<String> = _totalPrice
    val name: LiveData<String> = _name
    private val phone: LiveData<String> = _phone
    private val addressName: LiveData<String> = _addressName
    val orderStatus: LiveData<String> = _orderStatus

    fun setData(goodData: Good?) {
        _good.value = goodData
    }

    fun submitOrder() {
//        mallService.submitOrder(getOrder())
        _orderStatus.value = good.value?.sku
    }

    fun addGood() {
        val goodQuality = goodQuantity.value ?: QUANTITY_DEFAULT
        if (goodQuality < QUANTITY_MAXIMUM_LIMIT) {
            _goodQuantity.value = goodQuality + QUANTITY_INDEX
        } else {
            _addClickable.value = false
        }
        _reduceClickable.value = (goodQuantity.value ?: QUANTITY_DEFAULT) > QUANTITY_MINIMUM_LIMIT
    }

    fun reduceGood() {
        val goodQuality = goodQuantity.value ?: QUANTITY_DEFAULT
        if (goodQuality <= QUANTITY_MINIMUM_LIMIT) {
            _reduceClickable.value = false
        } else {
            _goodQuantity.value = goodQuality - QUANTITY_INDEX
        }
        _addClickable.value = (goodQuantity.value ?: QUANTITY_DEFAULT) < QUANTITY_MAXIMUM_LIMIT
    }

    fun updateGoodQuantity(quantity: Int) {
        _goodQuantity.value = quantity
    }

    fun updateTotalPrice() {
        _totalPrice.value =
            goodQuantity.value?.times(good.value?.price?.toDouble() ?: 0.0).toString()
    }

    fun isAddressValid(): Boolean {
        return RegexUtil.isValidText(name.value) && RegexUtil.isValidPhone(phone.value) && RegexUtil.isValidText(
            addressName.value
        )
    }

    fun getOrder(): Order {
        return Order().apply {
            sku = good.value?.sku
            quantity = goodQuantity.value
            price = good.value?.price
            total = totalPrice.value
            address = CustomerAddress(name.value, phone.value, addressName.value)
        }
    }
}