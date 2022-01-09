package com.example.mall.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.mall.R
import com.example.mall.databinding.ActivityOrderConfirmBinding
import com.example.mall.model.Good
import com.example.mall.util.RegexUtil
import com.example.mall.util.observeKeyboardChange
import com.example.mall.util.onTextChanged
import com.example.mall.viewmodel.OrderConfirmViewModel

class OrderConfirmActivity : AppCompatActivity() {
    companion object {
        private const val GOOD_DATA = "good_data"
        private const val QUANTITY_DEFAULT = 0
        private const val QUANTITY_MAXIMUM_LIMIT = 10
        private const val QUANTITY_MINIMUM_LIMIT = 1
        const val SUBMIT_ORDER_RESULT_CODE = 200
    }

    private lateinit var binding: ActivityOrderConfirmBinding
    private val goodData by lazy { intent.getParcelableExtra<Good>(GOOD_DATA) }
    private val viewModel by lazy {
        ViewModelProvider(this).get(OrderConfirmViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmBinding.inflate(layoutInflater).apply {
            vm = viewModel
            good = goodData
            lifecycleOwner = this@OrderConfirmActivity
        }
        setContentView(binding.root)
        viewModel.setData(goodData)
        initListener()
        initObserver()
    }

    private fun initListener() {
        binding.backButton.setOnClickListener {
            this.onBackPressed()
        }
        binding.name.onTextChanged {
            checkInputText(it, binding.invalidNameHint, viewModel._name)
        }
        binding.address.onTextChanged {
            checkInputText(it, binding.invalidAddressHint, viewModel._addressName)
        }
        checkPhoneNumber()
        binding.buyNow.setOnClickListener {
            if (viewModel.isAddressValid()) {
                viewModel.submitOrder()
            } else {
                Toast.makeText(this, R.string.invalid_address, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initObserver() {
        viewModel.goodQuantity.observe(this, {
            binding.totalNum.text =
                getString(R.string.total_num, it.toString())
            viewModel.updateTotalPrice()
        })
        this.observeKeyboardChange { show ->
            if (!show) {
                val quantity = binding.goodNum.text.toString()
                val goodQuantity = when {
                    quantity.isEmpty() || quantity.toInt() == QUANTITY_DEFAULT -> QUANTITY_MINIMUM_LIMIT
                    quantity.toInt() > QUANTITY_MAXIMUM_LIMIT -> {
                        Toast.makeText(this, R.string.good_num_limit_hint, Toast.LENGTH_SHORT)
                            .show()
                        QUANTITY_MAXIMUM_LIMIT
                    }
                    else -> {
                        quantity.toInt()
                    }
                }
                viewModel.updateGoodQuantity(goodQuantity)
                binding.goodNum.clearFocus()
            }
        }
        viewModel.orderStatus.observe(this, {
            if (it == goodData?.sku) {
                setResult(SUBMIT_ORDER_RESULT_CODE)
                finish()
            }
        })
    }

    private fun checkInputText(
        inputText: String,
        errorHintView: TextView,
        changedInfo: MutableLiveData<String>
    ) {
        if (inputText.isEmpty()) {
            showErrorHintView(errorHintView, getString(R.string.error_hint_required))
        } else if (!RegexUtil.isValidText(inputText)) {
            showErrorHintView(errorHintView, getString(R.string.error_hint_invalid_text))
        } else {
            errorHintView.visibility = View.GONE
            changedInfo.value = inputText
        }
    }

    private fun showErrorHintView(errorHintView: TextView, errorHint: String) {
        errorHintView.visibility = View.VISIBLE
        errorHintView.text = errorHint
    }

    private fun checkPhoneNumber() {
        binding.phone.onTextChanged {
            if (it.isEmpty()) {
                showErrorHintView(binding.invalidPhoneHint, getString(R.string.error_hint_required))
            } else {
                binding.invalidPhoneHint.visibility = View.GONE
                viewModel._phone.value = it
            }
        }
        val onFocusChangeListener = binding.phone.onFocusChangeListener
        binding.phone.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            onFocusChangeListener?.onFocusChange(v, hasFocus)
            if (!hasFocus && !RegexUtil.isValidPhone(binding.phone.text.toString())) {
                showErrorHintView(
                    binding.invalidPhoneHint,
                    getString(R.string.error_hint_invalid_phone)
                )
            }
        }
    }
}