package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

//
// Created by Noushad on 11-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class ScorecardResponse(
    @field:SerializedName("status")
    val status: Int,

    @field:SerializedName("success")
    val success: Boolean,
//        @field:SerializedName("status_code")
//        val statusCode: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("total_pages")
    val totalPages: Int = 0,

    @field:SerializedName("current_page")
    val currentPage: String? = null,

    @field:SerializedName("match_players_a")
    val matchPlayersA: List<MatchPlayers> = listOf(),

    @field:SerializedName("match")
    val match : TournamentMatchesList? = null,

    @field:SerializedName("match_players_b")
    val matchPlayersB: List<MatchPlayers> = listOf()
)

data class MatchPlayers(
    @field:SerializedName("player_id")
    val id: Int = 0,

    @field:SerializedName("player_position_id")
    val playerPositionId: Int = 0,

    @field:SerializedName("runs")
    val runs: Int = 0,

    @field:SerializedName("six")
    val six: Int = 0,

    @field:SerializedName("four")
    val four: Int = 0,

    @field:SerializedName("wickets")
    val wickets: Int = 0,

    @field:SerializedName("maiden_overs")
    val maidenOvers: Int = 0,

    @field:SerializedName("catches")
    val catches: Int = 0,

    @field:SerializedName("match_point")
    val matchPoint: String?=null,

    @field:SerializedName("player")
    val teamPlayers: Players?=null,

    @field:SerializedName("player_position")
    val playerPosition: PlayerPosition?=null
)




