package com.akirachix.totosteps.repository
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import com.akirachix.totosteps.api.ApiClient
import com.akirachix.totosteps.api.ApiInterface
import com.akirachix.totosteps.models.AutismResultResponse
import com.akirachix.totosteps.models.ImageUpload
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

class AutismImageRepository(private val context: Context) {
    private val apiInterface: ApiInterface = ApiClient.instance()

    suspend fun uploadPhoto(uri: Uri, childId: Int): Response<AutismResultResponse> {
        return withContext(Dispatchers.IO) {
            val compressedFile = compressImage(uri)
            val  mimeType = "image/jpeg"
            val imgRequestBody = RequestBody.create(mimeType.toMediaTypeOrNull(), compressedFile)
            val childIdReq = RequestBody.create("text/plain".toMediaTypeOrNull(), childId.toString())
            val imgReq = MultipartBody.Part.createFormData("image", compressedFile.name, imgRequestBody)
            apiInterface.uploadPhoto(imgReq, childIdReq)
        }
    }
    private fun getFileFromUri(uri: Uri, context: Context): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = getFileName(context, uri)
        val file = File(context.cacheDir, fileName)
        val outputStream = FileOutputStream(file)
        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
    private fun getFileName(context: Context, uri: Uri): String {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    if (index != -1) {
                        result = it.getString(index)
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/')
            if (cut != -1) {
                result = result?.substring(cut!! + 1)
            }
        }
        return result ?: "image.jpg"
    }
    private fun compressImage(uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        val maxSize = 1024
        val ratio = min(maxSize.toFloat() / originalBitmap.width, maxSize.toFloat() / originalBitmap.height)
        val width = (ratio * originalBitmap.width).toInt()
        val height = (ratio * originalBitmap.height).toInt()

        val compressedBitmap = Bitmap.createScaledBitmap(originalBitmap, width, height, true)

        val file = File(context.cacheDir, "compressed_image.jpg")
        val outputStream = FileOutputStream(file)
        compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        outputStream.flush()
        outputStream.close()

        return file
    }
    private fun queryName(resolver: ContentResolver, uri: Uri): String {
        val cursor = resolver.query(uri, null, null, null, null)!!
        val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        cursor.moveToFirst()
        val name = cursor.getString(nameIndex)
        cursor.close()
        return name
    }
}


