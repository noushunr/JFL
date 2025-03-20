package com.newagesmb.androidmvvmarchitecture.data.model.request

import com.google.gson.annotations.SerializedName
import com.newagesmb.androidmvvmarchitecture.utils.CommonUtils

//
// Created by Noushad on 15-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
data class UpdatePaymentRequest(
    @field:SerializedName("transaction_id")
    var transactionId: String = CommonUtils.generateRandomString(20),

    @field:SerializedName("user_tournament_team_id")
    var tournamentId: Int = 0,
)
