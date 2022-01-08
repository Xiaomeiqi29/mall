package com.example.mall.extension

inline fun <reified T> Any?.asTo(): T? {
    return this as? T
}