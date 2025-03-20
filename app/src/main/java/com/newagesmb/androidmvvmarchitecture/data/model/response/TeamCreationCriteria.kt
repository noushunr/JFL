package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class TeamCreationCriteria(
    @field:SerializedName("player_position_criteria")
    val playerPositionCriteria: List<PlayerPositions> = listOf(),

    @field:SerializedName("player_player_criteria")
    val playerCriteria: PlayerCriteria? = null,

    @field:SerializedName("total_tournament_teams")
    val totalTeams : Int = 0
)
