package com.example.mall.model

data class ResponseWrapper<T>(
    val code: Int?,
    val data: T?,
    val message: String?
)
