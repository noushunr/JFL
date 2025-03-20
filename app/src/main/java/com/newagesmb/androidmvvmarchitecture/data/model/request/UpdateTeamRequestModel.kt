package com.newagesmb.androidmvvmarchitecture.data.model.request

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 15-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class UpdateTeamRequestModel(

    @field:SerializedName("tournament_id")
    var tournamentId: Int = 0,

    @field:SerializedName("tournament_team_id")
    var tournamentTeamId: Int = 0,

    @field:SerializedName("tournament_team")
    val tournamentTeam: List<Int> = listOf(),

    @field:SerializedName("new_player")
    val newPlayer: List<Int> = listOf(),

    @field:SerializedName("new_player_name")
    val newPlayerName: List<String?> = listOf(),

    @field:SerializedName("new_player_position")
    val newPlayerPosition: List<Int> = listOf(),

    @field:SerializedName("new_player_captain")
    val newPlayerCaptain: List<Int> = listOf(),

    @field:SerializedName("new_player_vice_captain")
    val newPlayerViceCaptain: List<Int> = listOf(),
)
