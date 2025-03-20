package com.newagesmb.androidmvvmarchitecture.data.model.response
// Created by Noushad on 21-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("refresh_token")
	val refreshToken: String? = null,

	@field:SerializedName("session_id")
	val sessionId: String? = null,

	@field:SerializedName("token_expiry")
	val tokenExpiry: String? = null,

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("token")
	val token: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("mobile_number")
	val mobileNumber: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("date_of_birth")
	val dateOfBirth: String? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

)
