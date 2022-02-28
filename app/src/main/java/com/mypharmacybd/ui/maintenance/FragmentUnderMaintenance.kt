package com.mypharmacybd.ui.maintenance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mypharmacybd.R

class FragmentUnderMaintenance : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_under_maintenance, container, false)
    }

    companion object {

        @JvmStatic
        fun newInstance() = FragmentUnderMaintenance()
    }
}