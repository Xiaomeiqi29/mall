package com.example.mall.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mall.databinding.FragmentMyProfileBinding

class MyProfileFragment : Fragment() {
    companion object {
        fun newInstance(): MyProfileFragment {
            return MyProfileFragment()
        }
    }

    private lateinit var binding: FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}