package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class PlayerPositions(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("tournament_id")
    val tournamentId: Int = 0,

    @field:SerializedName("position_id")
    val positionId: Int = 0,

    @field:SerializedName("position_limit")
    val positionLimit: Int = 0,
)
