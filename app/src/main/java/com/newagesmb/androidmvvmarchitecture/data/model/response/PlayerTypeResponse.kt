package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class PlayerTypeResponse(

    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("success")
    val success: Boolean,
    @field:SerializedName("player_types")
    val playerTypes: List<PlayerTypes> = listOf(),
    @field:SerializedName("message")
    val message: String?=null,

)
