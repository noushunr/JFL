package com.newagesmb.androidmvvmarchitecture.data.model.response

import com.google.gson.annotations.SerializedName

//
// Created by Noushad on 05-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
data class PricePool(
    @SerializedName("rank")
    val rank : String ="1",

    @SerializedName("price")
    val price : Int =1,

)
