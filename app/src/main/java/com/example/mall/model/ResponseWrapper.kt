package com.example.mall.model

import java.net.HttpURLConnection

data class ResponseWrapper<T>(
    val code: Int?,
    val data: T?,
    val message: String?
)

fun <T> ResponseWrapper<T>.isSuccess(): Boolean {
    return this.code == HttpURLConnection.HTTP_OK
}