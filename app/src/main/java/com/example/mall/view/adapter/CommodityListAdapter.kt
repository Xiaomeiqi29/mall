package com.example.mall.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mall.databinding.ItemCommodityBinding
import com.example.mall.databinding.ItemCommodityWithTwoColumnBinding
import com.example.mall.extension.asTo
import com.example.mall.model.Commodity
import com.example.mall.view.CommodityDetailActivity
import com.example.mall.view.CommodityListFragment

class CommodityListAdapter(
    private val context: Context
) : RecyclerView.Adapter<CommodityListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    private var viewType: Int = CommodityListFragment.SPAN_COUNT
    private var commodities = emptyList<Commodity>()

    fun setData(commodityList: List<Commodity>) {
        commodities = commodityList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == CommodityListFragment.SPAN_COUNT) {
            ItemCommodityWithTwoColumnBinding.inflate(inflater, parent, false)
        } else {
            ItemCommodityBinding.inflate(inflater, parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (viewType == CommodityListFragment.SPAN_COUNT) {
            holder.binding.asTo<ItemCommodityWithTwoColumnBinding>()?.model = commodities[position]
        } else {
            holder.binding.asTo<ItemCommodityBinding>()?.model = commodities[position]
        }
        holder.itemView.setOnClickListener {
            val intent = CommodityDetailActivity.startIntent(context, commodities[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return commodities.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    fun setViewType(type: Int) {
        viewType = type
    }
}
