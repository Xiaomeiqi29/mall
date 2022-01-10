package com.example.mall.repository

import com.example.mall.model.Commodity
import com.example.mall.model.Order
import com.example.mall.model.ResponseWrapper

interface MallRepository {
    suspend fun getCommodityList(): ResponseWrapper<List<Commodity>>
    suspend fun createOrder(order: Order): ResponseWrapper<HashMap<String, String>>
}