package com.newagesmb.androidmvvmarchitecture.data.model.request
// Created by Noushad on 21-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import android.os.Build
import com.google.gson.annotations.SerializedName
import com.newagesmb.androidmvvmarchitecture.BuildConfig
import java.io.Serializable

data class ForgotPasswordRequestModel(

        @field:SerializedName("email")
        var email: String? = null,


) : Serializable{

}