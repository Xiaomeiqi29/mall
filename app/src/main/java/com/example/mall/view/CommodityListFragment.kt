package com.example.mall.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mall.R
import com.example.mall.databinding.FragmentCommodityListBinding
import com.example.mall.model.PageState
import com.example.mall.view.adapter.CommodityListAdapter
import com.example.mall.viewmodel.CommodityListViewModel

class CommodityListFragment : Fragment(), CompoundButton.OnCheckedChangeListener {
    companion object {
        private const val SINGLE_COLUMN_COUNT = 1
        const val SPAN_COUNT = 2
    }

    private lateinit var binding: FragmentCommodityListBinding
    private val gridLayoutManager by lazy { GridLayoutManager(requireActivity(), SPAN_COUNT) }
    private val commodityListAdapter: CommodityListAdapter by lazy {
        CommodityListAdapter(
            requireActivity()
        )
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(CommodityListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommodityListBinding.inflate(layoutInflater, container, false).apply {
            vm = viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserver()
        viewModel.loadCommodityList()
    }

    private fun initView() {
        binding.switchCount.setOnCheckedChangeListener(this)
        binding.commodityRecyclerview.layoutManager = gridLayoutManager
        binding.commodityRecyclerview.setHasFixedSize(true)
        binding.commodityRecyclerview.adapter = commodityListAdapter
        changeCommodityColumn(SPAN_COUNT)
    }

    private fun initObserver() {
        viewModel.pageState.observe(viewLifecycleOwner, {
            when (it) {
                PageState.LOADING -> {
                }
                PageState.SUCCESS -> {
                    commodityListAdapter.setData(viewModel.commodityList.value.orEmpty())
                }
                PageState.ERROR -> {
                    Toast.makeText(requireActivity(), R.string.error_hint, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        val count = if (isChecked) SPAN_COUNT else SINGLE_COLUMN_COUNT
        changeCommodityColumn(count)
    }

    private fun changeCommodityColumn(count: Int) {
        val i = 3 - count
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return i
            }
        }
        commodityListAdapter.setViewType(count)
        commodityListAdapter.notifyDataSetChanged()
    }
}