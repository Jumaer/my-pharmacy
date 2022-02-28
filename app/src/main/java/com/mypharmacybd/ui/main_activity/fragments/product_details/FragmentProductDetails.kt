package com.mypharmacybd.ui.main_activity.fragments.product_details

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.mypharmacybd.R
import com.mypharmacybd.data_models.Product
import com.mypharmacybd.databinding.FragmentProductDetailsBinding
import com.mypharmacybd.other.Constants
import com.mypharmacybd.ui.dialog.DialogConfirmAddCart
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class FragmentProductDetails : Fragment() {
    private var _binding:FragmentProductDetailsBinding? = null

    private val args by navArgs<FragmentProductDetailsArgs>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product: Product = args.product

        // set all data to view
        setDataToView(view = view, product = product)

        binding.addCart.setOnClickListener {
            Log.e(TAG, "onViewCreated: is called")
            //Show Add to cart Confirmation Dialog
            DialogConfirmAddCart(product).show(
                requireActivity().supportFragmentManager,
                DialogConfirmAddCart.TAG
            )
        }

        // implement Back Operation
        binding.topBar.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.topBar.searchBox.setOnClickListener {
            val action = FragmentProductDetailsDirections.actionFragmentProductDetailsToFragmentUnderMaintenance()
            findNavController().navigate(action)
        }
    }

    private fun setDataToView(view: View, product: Product) {
        binding.tvMedicineName.text = product.name
        binding.tvMedicineType.text = product.product_type?.name ?: ""
        binding.tvNewPrice.text = product.new_price
        binding.tvOldPrice.text = product.price


        val density = Resources.getSystem().displayMetrics.density
        val displayWidth = Resources.getSystem().displayMetrics.widthPixels
        val displayHeight: Int = (((displayWidth / 16.0) * 9.0) - (44 * density)).roundToInt()

        binding.isProductDetails.layoutParams.height = displayHeight
        binding.isProductDetails.requestLayout()

        val imageList = mutableListOf<SlideModel>()

        product.product_images?.forEach {
            val url = Constants.WEB_BASE_URL + it.file_path
            imageList.add(SlideModel(url, ScaleTypes.CENTER_INSIDE))
        }
        binding.isProductDetails.setImageList(imageList = imageList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "FragmentProductDetails"

        @JvmStatic
        fun newInstance() = FragmentProductDetails()
    }
}

