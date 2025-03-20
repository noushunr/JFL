package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.json.JsonNames

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class Players(
    @field:SerializedName(value = "player_id", alternate = ["id"])
    val id: Int = 0,

    @field:SerializedName("player_name")
    var playerName: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("age")
    val age: Int = 0,

    @field:SerializedName("is_foreigner")
    val isForeigner: Int = 0,

    @field:SerializedName("status")
    val status: Int = 0,

    var isSelected: Boolean = false,

    @field:SerializedName("player_position_id")
    var posId: Int = 0,

    @field:SerializedName("is_captain")
    var isCaptain: Int = 0,

    @field:SerializedName("is_vice_captain")
    var isViceCaptain: Int = 0,

    var teamCode : String = ""
)
