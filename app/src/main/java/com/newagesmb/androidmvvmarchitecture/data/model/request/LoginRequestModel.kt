package com.newagesmb.androidmvvmarchitecture.data.model.request
// Created by Noushad on 21-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import android.os.Build
import com.google.gson.annotations.SerializedName
import com.newagesmb.androidmvvmarchitecture.BuildConfig
import java.io.Serializable

data class LoginRequestModel(

        @field:SerializedName("email")
        var username: String? = null,
        @field:SerializedName("password")
        var password: String? = null,
        @field:SerializedName("fcm_id")
        var fcm_id: String? = "",
        @field:SerializedName("device_type")
        var device_type: String? = "Android",
        @field:SerializedName("info")
        var info:Info = Info(),

) : Serializable{
        data class Info(
                @field:SerializedName("device_type")
                var deviceType: String? = "ANDROID",
                @field:SerializedName("version")
                var version: String? = BuildConfig.VERSION_NAME,
                @field:SerializedName("android_version")
                var androidVersion: Int? = Build.VERSION.SDK_INT,
                @field:SerializedName("manufacturer")
                var manufacturer: String? = Build.MANUFACTURER,
                @field:SerializedName("model")
                var model: String? = Build.MODEL,
                @field:SerializedName("brand")
                var brand: String? = Build.BRAND,

                )
}