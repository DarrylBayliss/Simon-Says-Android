package com.darrylbayliss.simonsays.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.darrylbayliss.simonsays.R
import java.io.File

class SimonsSaysFileProvider() : FileProvider(R.xml.file_paths) {
    companion object {
        fun getImageUri(context: Context): Uri {

            val directory = File(context.cacheDir, "simon_says_images")
            directory.mkdirs()

            val file = File.createTempFile(
                "simon_says_image",
                ".jpg",
                directory,
            )

            return getUriForFile(context, "com.darrylbayliss.fileprovider", file)
        }
    }
}
