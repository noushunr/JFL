package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

//
// Created by Noushad on 11-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class TournamentMatchesList(
    @field:SerializedName("id")
    val id: Int =0 ,

    @field:SerializedName("tournament_id")
    val tournamentId: Int = 0,

    @field:SerializedName("team_a_id")
    val teamAId: Int = 0,

    @field:SerializedName("team_b_id")
    val teamBId: Int = 0,

    @field:SerializedName("match_date")
    val matchDate: String? = null,

    @field:SerializedName("match_time")
    val matchTime: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("match_playes_score_count")
    val matchPlayesScoreCount: Int = 0,

    @field:SerializedName("team_a")
    val teamA: Team?=null,

    @field:SerializedName("team_b")
    val teamB: Team?=null,

    @field:SerializedName("is_abandoned")
    val isAbandoned: Int?=0,

    var isEditEnabled: Boolean = false
)


