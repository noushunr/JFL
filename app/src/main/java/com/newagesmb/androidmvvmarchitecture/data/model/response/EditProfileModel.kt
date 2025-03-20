package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EditProfileModel(
    @SerializedName("gender")
    @Expose
    var gender: String,
    @SerializedName("phone")
    @Expose
    var phone: String,
    @SerializedName("phone_type")
    @Expose
    var phone_type: String,
    @SerializedName("recieve_type")
    @Expose
    var recieve_type: String,
    @SerializedName("country_id")
    @Expose
    var countryId: Int,
    @SerializedName("state_id")
    @Expose
    var stateId: Int,
    @SerializedName("country")
    @Expose
    val country: String?,
    @SerializedName("state")
    @Expose
    val state: String?,
    @SerializedName("city")
    @Expose
    var city: String,
    @SerializedName("unique_id")
    @Expose
    val uniqueId: Any,
    @SerializedName("email")
    @Expose
    val email: String,
    @SerializedName("first_name")
    @Expose
    val firstName: String,
    @SerializedName("suffix")
    @Expose
    val Suffix: String,
    @SerializedName("last_name")
    @Expose
    val lastName: String?,
    @SerializedName("middle_name")
    @Expose
    val MiddleName: String?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("address")
    @Expose
    var address: String?,
    @SerializedName("address2")
    @Expose
    var address2: String? = null,
    @SerializedName("dob")
    @Expose
    var dob: String?,
    @SerializedName("zip")
    @Expose
    var zip: String?,
    @SerializedName("country_code")
    @Expose
    var CountryCode: String?
)
