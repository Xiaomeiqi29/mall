package com.example.mall.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mall.R
import com.example.mall.databinding.ActivityGoodDetailBinding
import com.example.mall.model.Good

class GoodDetailActivity : AppCompatActivity() {
    companion object {
        private const val GOOD_DATA = "good_data"
        private const val ORDER_REQUEST_CODE = 100

        fun startIntent(context: Context, good: Good?) = Intent().apply {
            setClass(context, GoodDetailActivity::class.java)
            putExtra(GOOD_DATA, good)
        }
    }

    private lateinit var binding: ActivityGoodDetailBinding
    private val good by lazy { intent.getParcelableExtra<Good>(GOOD_DATA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = good
        initListener()
    }

    private fun initListener() {
        binding.appBar.backButton.setOnClickListener {
            this.onBackPressed()
        }
        binding.bottomBar.buyNow.setOnClickListener {
            val intent = Intent(this, OrderConfirmActivity::class.java).apply {
                putExtra(GOOD_DATA, good)
            }
            startActivityForResult(intent, ORDER_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == OrderConfirmActivity.SUBMIT_ORDER_RESULT_CODE) {
            Toast.makeText(this, R.string.submit_order_success, Toast.LENGTH_SHORT).show()
        }
    }
}