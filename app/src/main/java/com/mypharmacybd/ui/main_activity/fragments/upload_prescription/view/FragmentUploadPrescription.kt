package com.mypharmacybd.ui.main_activity.fragments.upload_prescription.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.mypharmacybd.R
import com.mypharmacybd.databinding.FragmentUploadPrescriptionBinding
import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.PrescriptionContact
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class FragmentUploadPrescription : Fragment(), PrescriptionContact.View {
    private var _binding: FragmentUploadPrescriptionBinding? = null

    private val binding get() = _binding!!

    private var cameraResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data: Intent? = it.data
            if (data != null) {
                binding.layoutTakePhoto.visibility = View.GONE
                binding.layoutPhotoViewer.visibility = View.VISIBLE
                setPicFromCamera()
            }
        }
    }

    private var galleryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            if (data != null) {
                val selectedImage = data.data
                if (selectedImage != null) {
                   getFileFromUri(requireActivity().contentResolver,
                    selectedImage, requireActivity().cacheDir)

                    setPicFromCamera()
                }
            }

        }
    }


    private lateinit var currentPhotoPath: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUploadPrescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topBar.tvTitle.text = getString(R.string.upload_prescription)
        binding.topBar.cart.ivCart.visibility = View.GONE
        binding.topBar.cart.tvCartCounter.visibility = View.GONE

        //dispatchTakeImageIntent()

        binding.btnRetry.setOnClickListener {
            implementBottomSheet(requireContext())
        }

        binding.btnUpload.setOnClickListener {
            val action = FragmentUploadPrescriptionDirections
                .actionFragmentUploadPrescriptionToFragmentUnderMaintenance()
            findNavController().navigate(action)
        }

        binding.btnTakePhoto.setOnClickListener {
            implementBottomSheet(requireContext())
        }

        implementBottomSheet(requireContext())
    }

    private fun implementBottomSheet(context: Context) {
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_take_picture_options)

        bottomSheetDialog.findViewById<View>(R.id.optionCamera)?.setOnClickListener {
            dispatchCameraIntent()
        }
        bottomSheetDialog.findViewById<View>(R.id.optionStorage)?.setOnClickListener {
            dispatchTakeFileIntent()
        }

        bottomSheetDialog.show()
    }

    private fun dispatchCameraIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takeImageIntent ->
            takeImageIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File = createImageFile()

                photoFile.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.mypharmacybd",
                        it
                    )
                    takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    cameraResultLauncher.launch(takeImageIntent)
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    private fun createImageFile(): File {
        // create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }


    private fun setPicFromCamera() {
        // get the dimension of the view
        val targetW = binding.ivPrescription.width
        val targetH = binding.ivPrescription.height

        Log.d(TAG, "setPic: target width = $targetW")
        Log.d(TAG, "setPic: target height = $targetH")


        val bmOptions = BitmapFactory.Options().apply {

            // get the dimension of the bitmaps
            inJustDecodeBounds = true
            BitmapFactory.decodeFile(currentPhotoPath, this)

            val photoW = outWidth
            val photoH = outHeight

            Log.d(TAG, "setPic: photo width = $photoW")
            Log.d(TAG, "setPic: photo height = $photoH")

            // determine how to scale down the image
            val scaleFactor: Int =
                1.coerceAtLeast((photoW / targetW).coerceAtMost(photoH / targetH))

            Log.d(TAG, "setPic: scale factor = $scaleFactor")

            // Decode the image file into into a bitmap sized to fill the view
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        BitmapFactory.decodeFile(currentPhotoPath, bmOptions)?.also { bitmap ->
            binding.ivPrescription.setImageBitmap(bitmap)

            Log.d(TAG, "setPic: final bitmap width = ${bitmap.width}")
            Log.d(TAG, "setPic: final bitmap height = ${bitmap.height}")
            Log.d(TAG, "setPic: final bitmap size = ${bitmap.byteCount}")
        }
    }

    private fun setPicFromGallery(imageUri: Uri) {

        val inputStream: InputStream? = requireActivity().contentResolver.openInputStream(imageUri)

        // get the dimension of the view
        val targetW = binding.ivPrescription.width
        val targetH = binding.ivPrescription.height

        val bmOptions = BitmapFactory.Options().apply {
            // get the dimension of the bitmaps
            inJustDecodeBounds = true

            BitmapFactory.decodeStream(inputStream, null, this).also {
                if(it == null){
                    Log.d(TAG, "setPicFromGallery: bitmap is null!")
                }
            }

            val photoW = outWidth
            val photoH = outHeight

            // determine how to scale down the image
            val scaleFactor: Int =
                1.coerceAtLeast((photoW / targetW).coerceAtMost(photoH / targetH))

            // Decode the image file into into a bitmap sized to fill the view
            inJustDecodeBounds = false
            inSampleSize = scaleFactor
        }

        BitmapFactory.decodeStream(inputStream, null, bmOptions)?.also { bitmap ->
            binding.ivPrescription.setImageBitmap(bitmap)
        }
    }

    private fun getFileFromUri(contentResolver: ContentResolver, uri: Uri, directory: File): File {
        val file = File.createTempFile("suffix", ".jpg", directory)
        file.outputStream().use {
            contentResolver.openInputStream(uri)?.copyTo(it)

        }



        return file.apply {
            currentPhotoPath = absolutePath
        }
    }

    private fun dispatchTakeFileIntent() {
        Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).also { takeImageIntent -> galleryResultLauncher.launch(takeImageIntent) }


        /*Intent(Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        ).also { takeImageIntent ->
            takeImageIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File = createImageFile()

                photoFile.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.mypharmacybd",
                        it
                    )
                    takeImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    galleryResultLauncher.launch(takeImageIntent)
                }
            }
        }*/
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUploadPrescription()
        private const val TAG = "FragmentUploadPrescript"
    }
}