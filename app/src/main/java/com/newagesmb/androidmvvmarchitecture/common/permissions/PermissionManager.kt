package com.newagesmb.androidmvvmarchitecture.common.permissions

// Created by Noushad on 01-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.newagesmb.androidmvvmarchitecture.R
import java.lang.ref.WeakReference

class PermissionManager private constructor(private val fragment: WeakReference<Fragment>) {

    private val requiredPermissions = mutableListOf<Permission>()
    private var rationale: String? = null
    private var callback: (Boolean) -> Unit = {}
    private var detailedCallback: (Map<Permission, Boolean>) -> Unit = {}

    private val permissionCheck =
        fragment.get()?.registerForActivityResult(RequestMultiplePermissions()) { grantResults ->
            sendResultAndCleanUp(grantResults)
        }

    companion object {
        fun from(fragment: Fragment) = PermissionManager(WeakReference(fragment))
    }

    fun rationale(description: String): PermissionManager {
        rationale = description
        return this
    }

    fun request(vararg permission: Permission): PermissionManager {
        requiredPermissions.addAll(permission)
        return this
    }

    fun checkPermission(callback: (Boolean) -> Unit) {
        this.callback = callback
        handlePermissionRequest()
    }

    fun checkDetailedPermission(callback: (Map<Permission, Boolean>) -> Unit) {
        this.detailedCallback = callback
        handlePermissionRequest()
    }

    private fun handlePermissionRequest() {
        fragment.get()?.let { fragment ->
            when {
                areAllPermissionsGranted(fragment) -> {
                    sendPositiveResult()

                }
                shouldShowPermissionRationale(fragment) -> {
                    displayRationale(fragment)


                }
                else -> {
                    requestPermissions()

                }
            }
        }
    }

    private fun displayRationale(fragment: Fragment) {
        val mDialogView = LayoutInflater.from(fragment.requireContext())
            .inflate(R.layout.permission_dialog_layout, null)
        val mBuilder = AlertDialog.Builder(fragment.requireContext())
            .setView(mDialogView)      //(fragment.requireContext(),R.style.CustomDialog).setView(mDialogView)//,R.style.CustomDialog)
        val permissionDialog = mBuilder.show()
        permissionDialog.setCancelable(false)
        permissionDialog.getWindow()!!.setBackgroundDrawableResource(R.color.transparent)
        val tittle = mDialogView.findViewById<TextView>(R.id.tx_tittle)
        val content = mDialogView.findViewById<TextView>(R.id.tx_msg_content)
        val cancel = mDialogView.findViewById<TextView>(R.id.tv_cancel)
        val action = mDialogView.findViewById<TextView>(R.id.tv_action)
        //  tittle.text="${fragment.getString(R.string.app_name)} needs your permission"
        content.text = rationale ?: fragment.getString(R.string.dialog_permission_default_message)
        cancel.setOnClickListener { permissionDialog.dismiss() }

        action.setOnClickListener {
            requestPermissions()
            permissionDialog.dismiss()
        }


    }

    private fun sendPositiveResult() {
        sendResultAndCleanUp(getPermissionList().associate { it to true })
    }

    private fun sendResultAndCleanUp(grantResults: Map<String, Boolean>) {
        callback(grantResults.all { it.value })
        detailedCallback(grantResults.mapKeys { Permission.from(it.key) })
        cleanUp()
    }

    private fun cleanUp() {
        requiredPermissions.clear()
        rationale = null
        callback = {}
        detailedCallback = {}
    }

    private fun requestPermissions() {
        permissionCheck?.launch(getPermissionList())
    }

    private fun areAllPermissionsGranted(fragment: Fragment) =
        requiredPermissions.all { it.isGranted(fragment) }

    private fun shouldShowPermissionRationale(fragment: Fragment) =
        requiredPermissions.any { it.requiresRationale(fragment) }

    private fun getPermissionList() =
        requiredPermissions.flatMap { it.permissions.toList() }.toTypedArray()

    private fun Permission.isGranted(fragment: Fragment) =
        permissions.all { hasPermission(fragment, it) }

    private fun Permission.requiresRationale(fragment: Fragment) =
        permissions.any { fragment.shouldShowRequestPermissionRationale(it) }

    private fun hasPermission(fragment: Fragment, permission: String) =
        ContextCompat.checkSelfPermission(
            fragment.requireContext(),
            permission
        ) == PackageManager.PERMISSION_GRANTED
}