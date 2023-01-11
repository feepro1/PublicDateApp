package com.main.core

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher

interface ManageImageRepository {

    fun onClickChooseImage(launcher: ActivityResultLauncher<Intent>, )

    class Base : ManageImageRepository {

        override fun onClickChooseImage(
            launcher: ActivityResultLauncher<Intent>,
        ) {
            val intentChooseImage = Intent()
            intentChooseImage.type = "image/*"
            intentChooseImage.action = Intent.ACTION_GET_CONTENT

            launcher.launch(intentChooseImage)
        }
    }
}