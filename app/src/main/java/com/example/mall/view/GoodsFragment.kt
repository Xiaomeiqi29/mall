package com.example.mall.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mall.databinding.FragmentGoodsBinding
import com.example.mall.model.Good
import com.example.mall.view.adapter.GoodsAdapter

class GoodsFragment : Fragment(), CompoundButton.OnCheckedChangeListener {
    companion object {
        private const val SINGLE_COLUMN_COUNT = 1
        const val SPAN_COUNT = 2
        fun newInstance(): GoodsFragment {
            return GoodsFragment()
        }

        private val MOCK_DATA = listOf(
            Good(
                "1",
                "iPhone 12 Pro Max",
                "https://media.router-switch.com/media/catalog/product/cache/b90fceee6a5fa7acd36a04c7b968181c/i/p/iphone-12-pro.jpg",
                "Apple/苹果手机iPhone 12 Pro Max 手机",
                "5999"
            ),
            Good(
                "2",
                "iphone13",
                "https://cdn.dxomark.com/wp-content/uploads/medias/post-101187/Apple-iPhone-13-Pro-featured-image-packshot-review-1-1024x691.jpg",
                "Apple/苹果手机iPhone 13远峰蓝",
                "7999"
            ),
            Good(
                "3",
                "iphone13",
                "https://loremflickr.com/cache/resized/5315_5844241027_9cb9f6ed24_n_180_180_nofilter.jpg",
                "Apple/苹果手机iPhone 13远峰蓝",
                "7999"
            ),
            Good(
                "4",
                "iphone13",
                "https://loremflickr.com/180/180?lock=1",
                "Apple/苹果手机iPhone 13远峰蓝",
                "7999"
            ),
        )
    }

    private lateinit var binding: FragmentGoodsBinding
    private val gridLayoutManager by lazy { GridLayoutManager(requireActivity(), SPAN_COUNT) }
    private lateinit var goodsAdapter: GoodsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoodsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadGoods()
    }

    private fun initView() {
        binding.switchCount.setOnCheckedChangeListener(this)

        binding.goodsRecyclerview.layoutManager = gridLayoutManager
        binding.goodsRecyclerview.setHasFixedSize(true)
        goodsAdapter = GoodsAdapter(requireActivity(), MOCK_DATA)
        binding.goodsRecyclerview.adapter = goodsAdapter

        changeGoodsColumn(SPAN_COUNT)
    }

    private fun loadGoods() {

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val count = if (isChecked) SPAN_COUNT else SINGLE_COLUMN_COUNT
        changeGoodsColumn(count)
    }

    private fun changeGoodsColumn(count: Int) {
        val i = 3 - count
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return i
            }
        }
        goodsAdapter.setViewType(count)
        goodsAdapter.notifyDataSetChanged()
    }
}