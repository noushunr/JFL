package com.newagesmb.androidmvvmarchitecture.data.model.request

import com.google.gson.annotations.SerializedName
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayersList

//
// Created by Noushad on 15-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class CreateTeamRequestModel(

    @field:SerializedName("tournament_id")
    var tournamentId: Int = 0,

    @field:SerializedName("players")
    val playersList: List<Players> = listOf(),
)
