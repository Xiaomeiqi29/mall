package com.example.mall.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mall.databinding.ItemGoodBinding
import com.example.mall.databinding.ItemGoodWithTwoColumnBinding
import com.example.mall.extension.asTo
import com.example.mall.model.Good
import com.example.mall.view.GoodDetailActivity
import com.example.mall.view.GoodsFragment

class GoodsAdapter(
    private val context: Context,
    private val goods: List<Good>
) : RecyclerView.Adapter<GoodsAdapter.ViewHolder>() {
    class ViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)

    private var viewType: Int = GoodsFragment.SPAN_COUNT

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = if (viewType == GoodsFragment.SPAN_COUNT) {
            ItemGoodWithTwoColumnBinding.inflate(inflater, parent, false)
        } else {
            ItemGoodBinding.inflate(inflater, parent, false)
        }
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (viewType == GoodsFragment.SPAN_COUNT) {
            holder.binding.asTo<ItemGoodWithTwoColumnBinding>()?.model = goods[position]
        } else {
            holder.binding.asTo<ItemGoodBinding>()?.model = goods[position]
        }
        holder.itemView.setOnClickListener {
            val intent = GoodDetailActivity.startIntent(context, goods[position])
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
        return goods.size
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    fun setViewType(type: Int) {
        viewType = type
    }
}
