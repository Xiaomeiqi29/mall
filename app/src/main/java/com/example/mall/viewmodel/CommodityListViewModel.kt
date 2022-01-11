package com.example.mall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mall.model.Commodity
import com.example.mall.model.PageState
import com.example.mall.model.isSuccess
import com.example.mall.repository.MallRepository
import com.example.mall.repository.MallRepositoryImpl
import kotlinx.coroutines.launch

class CommodityListViewModel : ViewModel() {
    private val mallRepository: MallRepository by lazy { MallRepositoryImpl.INSTANCE }
    private val _commodityList = MutableLiveData<List<Commodity>>()
    private val _pageState = MutableLiveData<PageState>(null)

    val commodityList: LiveData<List<Commodity>> = _commodityList
    val pageState: LiveData<PageState> = _pageState

    fun loadCommodityList() {
        _pageState.value = PageState.LOADING
        viewModelScope.launch {
            try {
                val data = mallRepository.getCommodityList()
                if (data.isSuccess()) {
                    _commodityList.value = data.data
                    _pageState.value = PageState.SUCCESS
                } else {
                    _pageState.value = PageState.ERROR
                }
            } catch (e: Exception) {
                _pageState.value = PageState.ERROR
            }
        }
    }
}