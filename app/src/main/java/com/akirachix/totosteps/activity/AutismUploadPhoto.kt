package com.akirachix.totosteps.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import java.io.IOException
import android.view.View
import com.akirachix.totosteps.databinding.ActivityAutismUploadPhotoBinding

class AutismUploadPhoto : AppCompatActivity() {
    lateinit var binding: ActivityAutismUploadPhotoBinding

    val PICK_IMAGE_REQUEST = 1
    var selectedImageUri: Uri? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAutismUploadPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set click listener for the upload button (image)
        binding.ivUpload.setOnClickListener {
            openImageChooser()
        }

        // Back button functionality
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        // Submit button functionality
        binding.btnSubmit.setOnClickListener {
            if (selectedImageUri != null) {
                submitImage()
            } else {
                Toast.makeText(this, "Please upload an image first", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Method to open the image picker
    fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    // Handle the image result from the file picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            selectedImageUri = data.data
            try {
                // Convert URI to Bitmap
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImageUri)
                // Set the Bitmap to the ImageView using binding
                binding.ivUpload.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Simulate image submission and show checkmark + success message
    fun submitImage() {
        // Hide the submit button
        binding.btnSubmit.visibility = View.GONE

        // Show the transparent overlay with checkmark and success message
        binding.successOverlay.visibility = View.VISIBLE

        // Delay for 2 seconds, then move to the next activity
        binding.successOverlay.postDelayed({
            // Hide the overlay (optional if you want to transition immediately)
            binding.successOverlay.visibility = View.GONE

            // Start the next activity
            val intent = Intent(this, ViewAutismResultsActivity::class.java) // Replace NextActivity with your next activity class
            startActivity(intent)

            // Optionally, if you don't want the user to come back to this screen, finish the current activity
            finish()

        }, 2000)  // Adjust delay as needed (2 seconds in this case)
    }
}