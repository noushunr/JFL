package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 16-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class TournamentPrizeMoney(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("tournament_id")
    val tournamentId: Int = 0,

    @field:SerializedName("start_position")
    val startPosition: Int? = 0,

    @field:SerializedName("end_position")
    val endPosition: Int? = 0,

    @field:SerializedName("prize_money")
    val prizeMoney: Int? = 0,
)
