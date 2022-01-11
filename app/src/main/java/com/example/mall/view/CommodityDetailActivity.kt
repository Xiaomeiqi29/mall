package com.example.mall.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mall.R
import com.example.mall.databinding.ActivityCommodityDetailBinding
import com.example.mall.model.Commodity

class CommodityDetailActivity : AppCompatActivity() {
    companion object {
        private const val COMMODITY_DATA = "commodity_data"
        private const val ORDER_REQUEST_CODE = 100

        fun startIntent(context: Context, commodity: Commodity?) = Intent().apply {
            setClass(context, CommodityDetailActivity::class.java)
            putExtra(COMMODITY_DATA, commodity)
        }
    }

    private lateinit var binding: ActivityCommodityDetailBinding
    private val commodity by lazy { intent.getParcelableExtra<Commodity>(COMMODITY_DATA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommodityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.model = commodity
        initListener()
    }

    private fun initListener() {
        binding.appBar.backButton.setOnClickListener {
            this.onBackPressed()
        }
        binding.bottomBar.buyNow.setOnClickListener {
            val intent = Intent(this, OrderConfirmActivity::class.java).apply {
                putExtra(COMMODITY_DATA, commodity)
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