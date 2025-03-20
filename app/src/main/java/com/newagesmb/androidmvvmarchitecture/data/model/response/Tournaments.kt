package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class Tournaments(
    @field:SerializedName("id")
    val id: Int = 0,

    @field:SerializedName("tournament_id")
    val tournamentId: Int = 0,

    @field:SerializedName("tournament_type")
    val tournamentType: String? = "",

    @field:SerializedName("tournament_name")
    val tournamentName: String? = "",

    @field:SerializedName("start_date")
    val startDate: String? = "",

    @field:SerializedName("start_time")
    val startTime: String? = "00:00:00",

    @field:SerializedName("end_date")
    val endDate: String? = "",

    @field:SerializedName("price")
    val price: String? = "",

    @field:SerializedName("image")
    val image: String? = "",

    @field:SerializedName("total_prize_money")
    val totalPrizeMoney: String? = "",

    @field:SerializedName("tournament_prize_money")
    val tournamentPrizeMoney: List<TournamentPrizeMoney>? = listOf(),

    @field:SerializedName("user_tournament_teams_count")
    val userTournamentTeamsCount: Int = 0,

    @field:SerializedName("tournament_players_count")
    val tournamentPlayersCount: Int = 0,
)
