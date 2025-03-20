package com.newagesmb.androidmvvmarchitecture.data.remote.apis

import com.google.gson.annotations.SerializedName
import com.newagesmb.androidmvvmarchitecture.data.model.response.Tournaments
import kotlinx.serialization.Serializable

/** Created by Noushad on 21-08-2023.
* Copyright (c) 2023 NewAgeSys. All rights reserved.
*/

/**
 * Base Gson class structure of all api responses.
 */
data class BaseResponse<T>(

        @field:SerializedName("message_code")
        val messageCode: String = "",

        @field:SerializedName("status")
        val status: Int = 1,

        @field:SerializedName("success")
        val success: Boolean = false,
//        @field:SerializedName("status_code")
//        val statusCode: Int,

        @field:SerializedName("message")
        val message: String = "",

        @field:SerializedName("data")
        var data: T?,

        @field:SerializedName("user_tournament_team_id")
        var userTournamentTeamId: Int = 0,

//        @field:SerializedName("tournament")
//        var tournament: Tournaments?,

)