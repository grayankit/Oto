package com.bheshnarayan.oto.utils

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.os.Build

fun AppCompatActivity.checkAudioPermissions(){
    val perm  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_AUDIO
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }
    checkPermissions(
        this,
        perm,
        "Permission Required",
        "This apps need to access your Music Library",
        {finish()}

    )?.launch(perm)


}

fun ActivityResultCaller.checkPermissions(
    context: Context,
    perm: String,
    title: String,
    message: String,
    onCancel: () -> Unit,
    onGranted: () -> Unit = {},
    onRequest: () -> Unit = {
        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            context.startActivity(this)
        }
    }
): ActivityResultLauncher<String>? = with(context) {
    val permStatus = ContextCompat.checkSelfPermission(this, perm)
    val contract = ActivityResultContracts.RequestPermission()
    return if (permStatus != PackageManager.PERMISSION_GRANTED) registerForActivityResult(contract) {
        if (!it) AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ -> onRequest() }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(
                    this,"Permission Denied", Toast.LENGTH_SHORT
                ).show()
                onCancel()
            }
            .show()
        else onGranted()
    } else null
}