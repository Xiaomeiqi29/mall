package com.example.mall.util

import android.app.Activity
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

private const val INITIAL_HEIGHT = 0
private const val HEIGHT_DIFF = 200
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
    var lastHeight = INITIAL_HEIGHT
    rootView.viewTreeObserver.addOnGlobalLayoutListener {
        rootView.getWindowVisibleDisplayFrame(rect)
        val height = rect.height()
        if (lastHeight == INITIAL_HEIGHT) {
            lastHeight = height
        } else {
            val diff = lastHeight - height
            if (diff > HEIGHT_DIFF) {
                onChange(true)
                lastHeight = height
            } else if (diff < -HEIGHT_DIFF) {
                onChange(false)
                lastHeight = height
            }
        }
    }
}