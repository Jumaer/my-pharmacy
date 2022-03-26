package com.mypharmacybd.ui.main_activity.fragments.order_list.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Category
import com.mypharmacybd.data_models.order.GetOrderResponse
import com.mypharmacybd.databinding.FragmentOrderListBinding
import com.mypharmacybd.ui.main_activity.fragments.categories.adapter.OrdersAdapter
import com.mypharmacybd.ui.main_activity.fragments.categories.view.FragmentCategoriesDirections
import com.mypharmacybd.ui.main_activity.fragments.order_list.OrderListContract
import com.mypharmacybd.user.Cart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FragmentOrderList : Fragment(), OrderListContract.View {
    private var _binding: FragmentOrderListBinding? = null

    private val binding get() = _binding!!
    @Inject
    lateinit var presenter: OrderListContract.Presenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setView(this)

        // set toolbar title and cart counter
        binding.appBarLayout.tvTitle.text = getString(R.string.order)

//        if (Cart.cartListLiveData != null) {
//            Cart.cartListLiveData?.observe(viewLifecycleOwner) {
//                binding.appBarLayout.cart.tvCartCounter.text = it.size.toString()
//            }
//        }
        presenter.getOrders()

        binding.appBarLayout.ivBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun showProgressbar() {

    }

    override fun hideProgressbar() {

    }

    override fun setDataToView(orderResponse: GetOrderResponse) {
        binding.rvOrder.adapter = OrdersAdapter(this, requireContext(), orderResponse)
    }

    override fun onCategoryClicked(orderResponse: GetOrderResponse) {
        // todo do it of details page
//        val action = FragmentCategoriesDirections
//            .actionFragmentCategoriesToFragmentCategoryDetails(orderResponse)
//        findNavController().navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: is called")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach: is called")
    }

    companion object{
        private const val TAG = "FragmentCategories"
    }
}