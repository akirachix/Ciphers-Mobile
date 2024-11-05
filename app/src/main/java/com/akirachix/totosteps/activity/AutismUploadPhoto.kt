package com.akirachix.totosteps.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akirachix.totosteps.activity.viewModel.AutismUploadPhotoViewModel
import com.akirachix.totosteps.databinding.ActivityAutismUploadPhotoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.File
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import androidx.appcompat.app.AlertDialog


class AutismUploadPhoto : AppCompatActivity() {
    private lateinit var binding: ActivityAutismUploadPhotoBinding
    private lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
    private val viewModel: AutismUploadPhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AutismUploadPhotoViewModel(application, childId) as T
            }
        }
    }
    private var photoUri: Uri? = null
    private var childId: Int = -1
    private var loadingDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutismUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBack.setOnClickListener {
            startActivity(Intent(this, SelectChildActivity::class.java))
        }

        childId = intent.getIntExtra("CHILD_ID", -1)

        if (childId == -1) {
            showError("Please select a child first")
            finish()
            return
        }

        setupPhotoSelection()
        setupClickListeners()
        observeViewModel()
        setupLoadingDialog()
    }

    private fun setupLoadingDialog() {
        loadingDialog = AlertDialog.Builder(this)
            .setTitle("Processing Photo")
            .setMessage("Please wait while we analyze the photo...")
            .setCancelable(false)
            .create()
    }

    private fun setupPhotoSelection() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                try {
                    showLoading("Processing photo...")

                    contentResolver.openInputStream(uri)?.use { stream ->
                        val size = stream.available()

                        if (size > 5 * 1024 * 1024) { // 5MB limit
                            hideLoading()
                            showError("Please select a smaller photo (max 5MB)")
                            return@registerForActivityResult
                        }

                        // Process and compress the image
                        val bitmap = BitmapFactory.decodeStream(stream)
                        val compressedFile = compressImage(bitmap)

                        if (compressedFile != null) {
                            photoUri = Uri.fromFile(compressedFile)

                            // Display the image
                            Glide.with(this)
                                .load(photoUri)
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .skipMemoryCache(true)
                                .into(binding.ivUpload)

                            hideLoading()
                            showSuccess("Photo selected successfully!")
                        } else {
                            hideLoading()
                            showError("Unable to process the photo. Please try another one.")
                        }
                    }
                } catch (e: Exception) {
                    hideLoading()
                    showError("Unable to process the photo. Please try another one.")
                    Log.e("PhotoUpload", "Error processing image", e)
                }
            }
        }
    }

    private fun compressImage(bitmap: Bitmap): File? {
        try {
            val file = File(cacheDir, "compressed_image_${System.currentTimeMillis()}.jpg")

            ByteArrayOutputStream().use { bos ->
                // Compress with 80% quality
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos)
                FileOutputStream(file).use { fos ->
                    fos.write(bos.toByteArray())
                }
            }

            return if (file.exists() && file.length() > 0) file else null
        } catch (e: Exception) {
            Log.e("PhotoUpload", "Error compressing image", e)
            return null
        }
    }

    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            uploadPhoto()
        }

        binding.ivUpload.setOnClickListener {
            showPhotoSelectionDialog()
        }
    }

    private fun showPhotoSelectionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Upload Photo")
            .setMessage("Please select a clear photo of the child's face for accurate assessment.")
            .setPositiveButton("Select Photo") { dialog, _ ->
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun uploadPhoto() {
        val currentPhotoUri = photoUri
        if (currentPhotoUri == null) {
            showError("Please select a photo first")
            return
        }

        try {
            showLoading("Uploading photo...")
            val file = prepareFileForUpload(currentPhotoUri)

            if (file == null) {
                hideLoading()
                showError("Unable to prepare the photo for upload. Please try again.")
                return
            }

            viewModel.uploadPhoto(currentPhotoUri, childId)
        } catch (e: Exception) {
            hideLoading()
            showError("Unable to upload the photo. Please try again.")
            Log.e("PhotoUpload", "Error preparing file for upload", e)
        }
    }

    private fun prepareFileForUpload(uri: Uri): File? {
        return try {
            when {
                uri.scheme == "file" -> File(uri.path!!)
                uri.scheme == "content" -> {
                    val inputStream = contentResolver.openInputStream(uri)
                    val tempFile = File(cacheDir, "upload_${System.currentTimeMillis()}.jpg")
                    inputStream?.use { input ->
                        FileOutputStream(tempFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                    tempFile
                }
                else -> null
            }
        } catch (e: Exception) {
            Log.e("PhotoUpload", "Error preparing file", e)
            null
        }
    }

    private fun observeViewModel() {
        viewModel.photoResponse.observe(this) { response ->
            hideLoading()
            val interpretedResult = viewModel.interpretResult(response.result)
            showResultDialog(interpretedResult)
        }

        viewModel.error.observe(this) { errorMessage ->
            hideLoading()
            showError(getReadableErrorMessage(errorMessage))
            Log.e("PhotoUpload", "Upload error: $errorMessage")
        }
    }

    private fun getReadableErrorMessage(error: String): String {
        return when {
            error.contains("400") -> "The photo couldn't be processed. Please ensure it shows a clear face and try again."
            error.contains("connection") -> "Please check your internet connection and try again."
            error.contains("timeout") -> "The upload is taking too long. Please try again with a smaller photo."
            else -> "Unable to upload the photo. Please try again."
        }
    }

    private fun showResultDialog(result: String) {
        AutismResultDialog.show(this, result) {
            navigateToHomePage()
        }
    }

    private fun navigateToHomePage() {
        startActivity(Intent(this, HomeScreenActivity::class.java))
        finish()
    }

    private fun showLoading(message: String) {
        loadingDialog?.setMessage(message)
        loadingDialog?.show()
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun showError(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}


