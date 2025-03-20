package com.newagesmb.androidmvvmarchitecture.data.model.response
// Created by Noushad on 21-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("file_url")
	val fileUrl: Any? = null,

	@field:SerializedName("google_id")
	val googleId: Any? = null,

	@field:SerializedName("last_login_at")
	val lastLoginAt: String? = null,

	@field:SerializedName("is_phone_verified")
	val isPhoneVerified: Int? = null,

	@field:SerializedName("occupation")
	val occupation: String? = null,

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("martial_status")
	val martialStatus: String? = null,

	@field:SerializedName("is_sponsored")
	val isSponsored: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("device_type")
	val deviceType: Any? = null,

	@field:SerializedName("clinic_manager")
	val clinicManager: Any? = null,

	@field:SerializedName("is_license_expired")
	val isLicenseExpired: Boolean? = null,

	@field:SerializedName("zip_code")
	val zipCode: String? = null,

	@field:SerializedName("about_me")
	val aboutMe: Any? = null,

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("role_id")
	val roleId: Int? = null,

	@field:SerializedName("provider")
	val provider: String? = null,

	@field:SerializedName("approved_at")
	val approvedAt: Any? = null,

	@field:SerializedName("state")
	val state: String? = null,

	@field:SerializedName("first_name")
	val firstName: String? = null,

	@field:SerializedName("clinic_id")
	val clinicId: Any? = null,

	@field:SerializedName("fcm_id")
	val fcmId: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("acronym_id")
	val acronymId: Any? = null,

	@field:SerializedName("address")
	val address: String? = null,

	@field:SerializedName("npi")
	val npi: Any? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("firebase_id")
	val firebaseId: Any? = null,

	@field:SerializedName("active")
	val active: Boolean? = null,

	@field:SerializedName("last_name")
	val lastName: String? = null,

	@field:SerializedName("apple_id")
	val appleId: Any? = null,

	@field:SerializedName("is_password_changed")
	val isPasswordChanged: Int? = null,

	@field:SerializedName("created_by")
	val createdBy: Any? = null,

	@field:SerializedName("no_of_children")
	val noOfChildren: String? = null,

	@field:SerializedName("facebook_id")
	val facebookId: Any? = null,

	@field:SerializedName("country_code")
	val countryCode: String? = null,

	@field:SerializedName("full_name")
	val fullName: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("dob")
	val dob: String? = null,

	@field:SerializedName("updated_by")
	val updatedBy: Any? = null,

	@field:SerializedName("status")
	val status: String? = null
)
