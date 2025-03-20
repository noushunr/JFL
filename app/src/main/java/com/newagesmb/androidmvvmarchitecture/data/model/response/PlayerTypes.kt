package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class PlayerTypes(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("role")
    val role: String? = null,

    @field:SerializedName("role_code")
    var roleCode: String? = null,
)
