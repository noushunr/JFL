package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class TournamentStats(
    @field:SerializedName("contestant_id")
    val id: Int = 0,

    @field:SerializedName("contestant_name")
    val contestantName: String? = null,

    @field:SerializedName("contestant_image")
    val image: String? = null,

    @field:SerializedName("point")
    val point: String? = null
)
