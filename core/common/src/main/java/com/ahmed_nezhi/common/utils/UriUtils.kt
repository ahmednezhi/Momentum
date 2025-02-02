package com.ahmed_nezhi.common.utils

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import java.io.File

/**
 * Creates a temporary image URI for capturing photos.
 */
fun createImageUri(context: Context): Uri {
    val filename = "JPEG_${System.currentTimeMillis()}.jpg"
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val file = File.createTempFile(filename, ".jpg", storageDir)
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )
}