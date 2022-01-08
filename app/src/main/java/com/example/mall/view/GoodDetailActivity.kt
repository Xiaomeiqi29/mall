package com.example.mall.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mall.databinding.ActivityGoodDetailBinding
import com.example.mall.model.Good

class GoodDetailActivity : AppCompatActivity() {
    companion object {
        private const val GOOD_DATA = "good_data"

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
    }
}