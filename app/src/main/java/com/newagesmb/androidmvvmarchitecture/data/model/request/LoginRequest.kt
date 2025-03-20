package com.newagesmb.androidmvvmarchitecture.data.model.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginRequest(
    @field:SerializedName("email")
    var email: String? = null,
    @field:SerializedName("password")
    var password: String? = null,
): Serializable
