package com.example.mall.util

import android.app.Activity
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.onTextChanged(block: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            block(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {
        }

    })
}


fun Activity.observeKeyboardChange(onChange: (show: Boolean) -> Unit) {
    val rootView = this.window.decorView
    val rect = Rect()
    var lastHeight = 0
    rootView.viewTreeObserver.addOnGlobalLayoutListener {
        rootView.getWindowVisibleDisplayFrame(rect)
        val height = rect.height()
        if (lastHeight == 0) {
            lastHeight = height
        } else {
            val diff = lastHeight - height
            if (diff > 200) {
                onChange(true)
                lastHeight = height
            } else if (diff < -200) {
                onChange(false)
                lastHeight = height
            }
        }
    }
}