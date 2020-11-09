package com.nourtayeb.movies_mvi.common.utility

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


fun View.clicks(): Flow<Unit> = callbackFlow {
    this@clicks.setOnClickListener {
        this.offer(Unit)
    }
    awaitClose { this@clicks.setOnClickListener(null) }
}
fun AppCompatActivity.shareBitmap(it: Bitmap) {
    Dexter.withContext(this)
        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        .withListener(object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) { /* ... */
                val bitmapPath: String =
                    MediaStore.Images.Media.insertImage(contentResolver, it, "title", "Share image")
                val bitmapUri: Uri = Uri.parse(bitmapPath)

                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "image/png"
                intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)
                startActivity(Intent.createChooser(intent, "Share"))
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) { /* ... */

            }

            override fun onPermissionRationaleShouldBeShown(
                permission: PermissionRequest?,
                token: PermissionToken?
            ) { /* ... */
            }
        }).check()




}