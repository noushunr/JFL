package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName

//
// Created by Noushad on 11-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class MyMatchesList(
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

    @field:SerializedName("tournaments")
    val tournaments: List<Tournaments> = listOf(),

    @field:SerializedName("tournament")
    val tournament : Tournaments? = null,

    @field:SerializedName("team_list")
    val teamList: List<TeamList> = listOf()
)

data class TeamList(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("tournament_id")
    val tournamentId: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("payment_status")
    val paymentStatus: Int,

    @field:SerializedName("team_status")
    val teamStatus: Int,

//    @field:SerializedName("transaction_id")
//    val transactionId: String,

    @field:SerializedName("tournament_point")
    val tournamentPoint: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("team_players")
    val teamPlayers: List<TeamPlayer>,

    var isEditEnabled: Boolean = false
)

data class TeamPlayer(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("user_tournament_team_id")
    val userTournamentTeamId: Int,

    @field:SerializedName("player_id")
    val playerId: Int,

    @field:SerializedName("player_position_id")
    val playerPositionId: Int,

    @field:SerializedName("is_captain")
    val isCaptain: Int,

    @field:SerializedName("is_vice_captain")
    val isViceCaptain: Int,

    @field:SerializedName("player")
    val player: Player,

    @field:SerializedName("player_position")
    val playerPosition: PlayerPosition,

    val teamCode:String =""
)

data class Player(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("player_name")
    val playerName: String,

    @field:SerializedName("status")
    val status: Int
)

data class PlayerPosition(
    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("role_code")
    val roleCode: String
)

