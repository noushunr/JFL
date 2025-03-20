package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class PlayerCriteria(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("tournament_id")
    val tournamentId: Int = 0,

    @field:SerializedName("player_limit")
    val playerLimit: Int = 0,

    @field:SerializedName("min_player_limit_per_team")
    val minPlayerLimitPerTeam: Int = 0,

    @field:SerializedName("max_player_limit_per_team")
    val maxPlayerLimitPerTeam: Int = 0,

    @field:SerializedName("foreigner_player_limit")
    val foreignerPlayerLimit: Int = 0,
)
