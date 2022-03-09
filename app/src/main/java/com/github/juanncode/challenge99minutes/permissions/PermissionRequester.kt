package com.github.juanncode.challenge99minutes.permissions

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

class PermissionRequester(activity: ComponentActivity, val permission: String, val onDenied: ()-> Unit, val onRationale: ()-> Unit) {

    private var onGranted : () -> Unit = {}

    private val permissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            isGranted ->

        when {
            isGranted -> onGranted()
            activity.shouldShowRequestPermissionRationale(permission) -> onRationale()
            else -> onDenied()
        }
    }

    fun runWithPermission(body: ()-> Unit) {
        onGranted = body
        permissionLauncher.launch(permission)

    }
}