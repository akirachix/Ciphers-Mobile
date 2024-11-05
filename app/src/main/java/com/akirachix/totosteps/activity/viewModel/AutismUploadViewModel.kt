package com.akirachix.totosteps.activity.viewModel
import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akirachix.totosteps.models.AutismResultResponse
import com.akirachix.totosteps.models.ImageUpload
import com.akirachix.totosteps.repository.AutismImageRepository
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject

class AutismUploadPhotoViewModel(application: Application, private val childId: Int) : AndroidViewModel(application) {
    private val photoRepo = AutismImageRepository(application)

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _photoResponse = MutableLiveData<AutismResultResponse>()
    val photoResponse: LiveData<AutismResultResponse> = _photoResponse

    fun uploadPhoto(uri: Uri, childId: Int) {
        viewModelScope.launch {

            try {
                val response = photoRepo.uploadPhoto(uri, childId)
                if (response.isSuccessful) {
                    response.body()?.let { resultResponse ->
                        _photoResponse.postValue(resultResponse)
                    } ?: run {
                        _error.postValue("Response body is null")
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        JSONObject(errorBody ?: "").getString("message")
                    } catch (e: Exception) {
                        "Unknown error occurred"
                    }
                    _error.postValue("Server error: ${response.code()} - $errorMessage")
                }
            } catch (e: Exception) {
                Log.e("UploadError", "Exception during upload", e)
                _error.postValue("Exception: ${e.message}")
            }
        }
    }

    fun interpretResult(result: String): String {
        return when (result.toLowerCase()) {
            "low autism risk" -> "Dear parent, based on the image analysis, your child does not show signs of autism. However, if you have concerns, please consult with a healthcare professional."
            "highly autistic" -> "Dear parent, your child is showing symptoms of autism. Kindly visit the nearest healthcare provider for more analysis and information."
            "non autistic" -> "Dear parent, based on the image analysis, your child does not show signs of autism. However, if you have concerns, please consult with a healthcare professional."
            "moderate autism risk" ->"Dear parent, your child is showing minimal risk of autism. Kindly seek medical attention from the nearest healthcare provider."
            else -> "An error occurred while processing the image. Please try again."
        }
    }
}

private fun <T> MutableLiveData<T>.postValue(resultResponse: ImageUpload) {
    TODO("Not yet implemented")
}
