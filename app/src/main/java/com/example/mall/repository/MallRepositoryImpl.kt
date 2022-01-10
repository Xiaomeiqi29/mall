package com.example.mall.repository

import com.example.mall.model.Commodity
import com.example.mall.model.Order
import com.example.mall.model.ResponseWrapper
import com.example.mall.service.MallService
import com.example.mall.service.ServiceCreator

class MallRepositoryImpl private constructor() : MallRepository {
    companion object {
        val INSTANCE by lazy { MallRepositoryImpl() }
    }

    private val mallService = ServiceCreator.create(MallService::class.java)

    override suspend fun getCommodityList(): ResponseWrapper<List<Commodity>> {
        return mallService.getCommodityList()
    }

    override suspend fun createOrder(order: Order): ResponseWrapper<HashMap<String, String>> {
        return mallService.createOrder(order)
    }
}