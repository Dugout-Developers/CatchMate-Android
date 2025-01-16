package com.catchmate.presentation.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL

object ImageUtils {
    suspend fun convertUrlToMultipart(
        context: Context,
        url: String,
    ): MultipartBody.Part =
        withContext(Dispatchers.IO) {
            try {
                val url = URL(url)
                val connection = url.openConnection()
                val inputStream = BufferedInputStream(connection.getInputStream())
                val file = File(context.cacheDir, "downloadedFile")
                inputStream.use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
                val requestFile = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("profileImage", file.name, requestFile)
            } catch (e: Exception) {
                Log.e("기본 이미지 URL 변환 실패", e.message.toString())
                val emptyFile = "".toRequestBody("multipart/form-data".toMediaTypeOrNull())
                MultipartBody.Part.createFormData("profileImage", "", emptyFile)
            }
        }

    fun convertBitmapToMultipart(
        context: Context,
        bitmap: Bitmap,
    ): MultipartBody.Part {
        val file = File(context.cacheDir, "profile_image.jpg")
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
        }

        val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val multipart = MultipartBody.Part.createFormData("profileImage", file.name, requestFile)
        return multipart
    }
}
