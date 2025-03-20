package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class TournamentListResponse(
    @field:SerializedName("total_pages")
    val totalPages: Int = 0,

    @field:SerializedName("current_page")
    val currentPage: String? = null,

    @field:SerializedName("tournaments")
    val tournaments: List<Tournaments> = listOf(),

)
