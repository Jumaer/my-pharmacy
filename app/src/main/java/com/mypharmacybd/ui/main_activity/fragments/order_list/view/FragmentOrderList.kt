package com.mypharmacybd.ui.main_activity.fragments.order_list.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentCategoriesBinding
import com.mypharmacybd.databinding.FragmentOrderListBinding


class FragmentOrderList : Fragment() {
    private var _binding: FragmentOrderListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }


}