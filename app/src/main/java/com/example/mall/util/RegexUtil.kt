package com.example.mall.util

import java.util.regex.Pattern

object RegexUtil {
    fun isValidPhone(mobiles: String?): Boolean {
        return if (mobiles == null) {
            false
        } else {
            val p = Pattern
                .compile("^((1[3456789][0-9]))[0-9]{8}$")
            val m = p.matcher(mobiles)
            m.matches()
        }
    }

    fun isValidText(name: String?): Boolean {
        val p = Pattern.compile("^[a-zA-Z \\u4e00-\\u9fa5[0-9]]+$")
        val m = p.matcher(name)
        return m.matches()
    }
}
