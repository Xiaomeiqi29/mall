package com.example.mall.service

import com.example.mall.model.Commodity
import com.example.mall.model.Order
import com.example.mall.model.ResponseWrapper
import retrofit2.http.GET
import retrofit2.http.POST

interface MallService {
    @GET("commodity/list")
    suspend fun getAddressList(): ResponseWrapper<Commodity>

    @POST("order/create")
    suspend fun createOrder(order: Order): ResponseWrapper<HashMap<String, String>>
}