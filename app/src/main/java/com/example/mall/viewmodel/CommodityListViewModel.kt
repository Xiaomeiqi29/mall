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
    private val _isLoading = MutableLiveData(false)
    private val _showError = MutableLiveData(false)

    val commodityList: LiveData<List<Commodity>> = _commodityList
    val isLoading: LiveData<Boolean> = _isLoading
    val showError: LiveData<Boolean> = _showError

    fun loadCommodityList() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val data = mallRepository.getCommodityList()
                Log.d("pppppppp", "loadCommodityList: ${data.data.toString()}")
                if (data.isSuccess()) {
                    _commodityList.value = data.data
                    _isLoading.value = false
                } else {
                    _showError.value = true
                }
            } catch (e: Exception) {
                Log.d("pppppppp", "Exception: ${e.message}")
                _showError.value = true
            }
        }
    }
}