package com.newagesmb.androidmvvmarchitecture.data.model.response


import com.google.gson.annotations.SerializedName

data class LoginModel(
        @field:SerializedName("user_id")
         var userId: Int,
        @field:SerializedName("unique_id")
        var unique_id: String?,
        @field:SerializedName("email")
        var email: String,
        @field:SerializedName("user_type")
        var userType: Int,
        @field:SerializedName("first_name")
        var firstName: String,
        @field:SerializedName("last_name")
        var lastName: String,
        @field:SerializedName("middle_name")
        var middle_name: String?,
        @field:SerializedName("suffix")
        var suffix: String?,
        @field:SerializedName("image")
        var image: String?,
        @field:SerializedName("active")
        var active: String,
        @field:SerializedName("qr_code_url")
        var qr_code_url: String,
        @field:SerializedName("email_verified")
        var email_verified: String,
        var fullname: String?
)