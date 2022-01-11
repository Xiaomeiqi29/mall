package com.example.mall.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mall.model.Commodity
import com.example.mall.model.isSuccess
import com.example.mall.repository.MallRepository
import com.example.mall.repository.MallRepositoryImpl
import kotlinx.coroutines.launch

class CommodityListViewModel : ViewModel() {
    private val mallRepository: MallRepository by lazy { MallRepositoryImpl.INSTANCE }
    private val _commodityList = MutableLiveData<List<Commodity>>()
    private val _showError = MutableLiveData(false)

    val commodityList: LiveData<List<Commodity>> = _commodityList
    val showError: LiveData<Boolean> = _showError

    fun loadCommodityList() {
        viewModelScope.launch {
            try {
                val data = mallRepository.getCommodityList()
                if (data.isSuccess()) {
                    _commodityList.value = data.data
                } else {
                    _showError.value = true
                }
            } catch (e: Exception) {
                _showError.value = true
            }
        }
    }
}