package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class Team(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("team")
    val team: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("team_code")
    val teamCode: String? = null
)
