package com.example.mall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mall.model.*
import com.example.mall.repository.MallRepository
import com.example.mall.repository.MallRepositoryImpl
import com.example.mall.util.RegexUtil
import kotlinx.coroutines.launch

class OrderConfirmViewModel : ViewModel() {
    companion object {
        private const val QUANTITY_DEFAULT = 0
        private const val QUANTITY_MAXIMUM_LIMIT = 10
        private const val QUANTITY_MINIMUM_LIMIT = 1
        private const val QUANTITY_INDEX = 1
    }

    private val mallRepository: MallRepository by lazy { MallRepositoryImpl.INSTANCE }
    private val _commodity = MutableLiveData(Commodity())
    private val _commodityQuantity = MutableLiveData(1)
    private val _addClickable = MutableLiveData(true)
    private val _reduceClickable = MutableLiveData(true)
    private val _totalPrice = MutableLiveData("")
    private val _pageState = MutableLiveData<PageState>(null)
    val name = MutableLiveData("")
    val phone = MutableLiveData("")
    val addressName = MutableLiveData("")

    val commodity: LiveData<Commodity> = _commodity
    val commodityQuantity: LiveData<Int> = _commodityQuantity
    val addClickable: LiveData<Boolean> = _addClickable
    val reduceClickable: LiveData<Boolean> = _reduceClickable
    val totalPrice: LiveData<String> = _totalPrice
    val pageState: LiveData<PageState> = _pageState

    fun setData(commodityData: Commodity?) {
        _commodity.value = commodityData
    }

    fun submitOrder() {
        _pageState.value = PageState.LOADING
        viewModelScope.launch {
            try {
                val data = mallRepository.createOrder(getOrder())
                if (data.isSuccess() && data.data?.isNotEmpty() == true) {
                    _pageState.value = PageState.SUCCESS
                } else {
                    _pageState.value = PageState.ERROR
                }
            } catch (e: Exception) {
                _pageState.value = PageState.ERROR
            }
        }
    }

    fun addCommodity() {
        val commodityQuality = commodityQuantity.value ?: QUANTITY_DEFAULT
        if (commodityQuality < QUANTITY_MAXIMUM_LIMIT) {
            _commodityQuantity.value = commodityQuality + QUANTITY_INDEX
        } else {
            _addClickable.value = false
        }
        _reduceClickable.value =
            (commodityQuantity.value ?: QUANTITY_DEFAULT) > QUANTITY_MINIMUM_LIMIT
    }

    fun reduceCommodity() {
        val commodityQuality = commodityQuantity.value ?: QUANTITY_DEFAULT
        if (commodityQuality <= QUANTITY_MINIMUM_LIMIT) {
            _reduceClickable.value = false
        } else {
            _commodityQuantity.value = commodityQuality - QUANTITY_INDEX
        }
        _addClickable.value = (commodityQuantity.value ?: QUANTITY_DEFAULT) < QUANTITY_MAXIMUM_LIMIT
    }

    fun updateCommodityQuantity(quantity: Int) {
        _commodityQuantity.value = quantity
    }

    fun updateTotalPrice() {
        _totalPrice.value =
            commodityQuantity.value?.times(commodity.value?.price?.toDouble() ?: 0.0).toString()
    }

    fun isAddressValid(): Boolean {
        return RegexUtil.isValidText(name.value) && RegexUtil.isValidPhone(phone.value) && RegexUtil.isValidText(
            addressName.value
        )
    }

    private fun getOrder(): Order {
        return Order().apply {
            sku = commodity.value?.sku
            quantity = commodityQuantity.value
            price = commodity.value?.price
            total = totalPrice.value
            address = CustomerAddress(name.value, phone.value, addressName.value)
        }
    }
}