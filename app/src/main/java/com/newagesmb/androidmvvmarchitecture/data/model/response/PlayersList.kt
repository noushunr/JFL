package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class PlayersList(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("team_id")
    val teamId: Int = 0,

    @field:SerializedName("player_id")
    val playerId: Int = 0,

    @field:SerializedName("player")
    val player: Players? = null,

    @field:SerializedName("team")
    val team: Team? = null,

    @field:SerializedName("tournament")
    val tournament: Tournaments?= null,

)

