package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class MatchListResponse(
    @field:SerializedName("current_page")
    val currentPage: String = "",
    @field:SerializedName("total_pages")
    val totalPages: Int = 0,
    @field:SerializedName("tournament_matches")
    val tournamentMatches: List<TournamentMatchesList> = listOf(),

)
