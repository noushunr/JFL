package com.newagesmb.androidmvvmarchitecture.data.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RegisterRequest(
    @field:SerializedName("email")
    var username: String? = null,
    @field:SerializedName("password")
    var password: String? = null,
    @field:SerializedName("name")
    var name: String? = "",
    @field:SerializedName("password_confirmation")
    var passwordConfirmation: String? = "",
    @field:SerializedName("mobile_number")
    var mobileNumber: String? = "",
): Serializable
