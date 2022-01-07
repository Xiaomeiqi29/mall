package com.example.mall.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mall.databinding.FragmentGoodsBinding

class GoodsFragment : Fragment() {
    companion object {
        fun newInstance(): GoodsFragment {
            return GoodsFragment()
        }
    }

    private lateinit var binding: FragmentGoodsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGoodsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}