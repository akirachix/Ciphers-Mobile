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
import com.akirachix.totosteps.R
import com.akirachix.totosteps.activity.viewModel.AutismUploadPhotoViewModel
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.databinding.ActivityAutismUploadPhotoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutismUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        childId = intent.getIntExtra("CHILD_ID", -1)

        if (childId == -1) {
            Toast.makeText(this, "No child selected", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        setupPhotoSelection()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupPhotoSelection() {
        pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let {
                photoUri = it
                Glide.with(this)
                    .load(uri)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(binding.ivUpload)
            }
        }
    }

    private fun setupClickListeners() {
        binding.btnSubmit.setOnClickListener {
            uploadPhoto()
        }

        binding.ivUpload.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun uploadPhoto() {
        val currentPhotoUri = photoUri
        if (currentPhotoUri == null) {
            Toast.makeText(this, "Please select a photo", Toast.LENGTH_LONG).show()
            return
        }

        viewModel.uploadPhoto(currentPhotoUri, this.childId)
    }
    private fun observeViewModel() {
        viewModel.photoResponse.observe(this) { response ->
            val interpretedResult = viewModel.interpretResult(response.result)
            showResultDialog(interpretedResult)
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }
    private fun showResultDialog(result: String) {
        Log.d("AutismUploadPhoto", "Showing result dialog")
        AutismResultDialog.show(this, result) {
            navigateToHomePage()
        }
    }
    private fun navigateToHomePage() {
        val intent = Intent(
            this,
            HomeScreenActivity::class.java
        )
        startActivity(intent)
        finish()
    }
}



