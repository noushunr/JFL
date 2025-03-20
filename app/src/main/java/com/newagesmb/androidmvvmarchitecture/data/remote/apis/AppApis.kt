package com.newagesmb.androidmvvmarchitecture.data.remote.apis
// Created by Noushad on 21-08-2023.
// Copyright (c) 2023 NewAgeSys. All rights reserved.
//
import androidx.lifecycle.LiveData
import com.newagesmb.androidmvvmarchitecture.data.model.request.CreateTeamRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.request.ForgotPasswordRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.request.RegisterRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.UpdatePaymentRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.UpdateTeamRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.EditProfileModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.MatchListResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.MyMatchesList
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerListResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerTypeResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.ScorecardResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.StatsListResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamCreationCriteria
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentListResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface AppApis {

    @POST("user/login")
    fun login(@Body loginRequestModel: LoginRequestModel): Flow<ApiResponse<BaseResponse<LoginResponse>>>

    @POST("user/register")
    fun register(@Body registerRequest: RegisterRequest): Flow<ApiResponse<BaseResponse<LoginResponse>>>

    @POST("users/login")
    fun login(@Body requestBody: LoginRequest): Flow<ApiResponse<BaseResponse<LoginModel>>>

    @POST("user/forgot-password")
    fun forgotPassword(@Body requestModel: ForgotPasswordRequestModel): Flow<ApiResponse<BaseResponse<LoginResponse>>>

    @POST("users/get_profile_details")
    fun get_profile_details(): Flow<ApiResponse<BaseResponse<EditProfileModel>>>

    @GET("user/tournament-list")
    fun getTournamentList() : Flow<ApiResponse<BaseResponse<TournamentListResponse>>>

    @GET("user/team-creation-criteria")
    fun getTeamCreationCriteria(@Query("tournament_id") id:Int) : Flow<ApiResponse<BaseResponse<TeamCreationCriteria>>>

    @GET("user/player-types")
    fun getPlayerTypes(@Query("tournament_id") id:Int) : Flow<ApiResponse<PlayerTypeResponse>>

    @GET("user/players-list")
    fun getPlayerListByTypes(@Query("player_type_id") playerType:Int,@Query("tournament_id") id:Int) : Flow<ApiResponse<BaseResponse<PlayerListResponse>>>

    @GET("user/players-list")
    fun paginatePlayerList(@Query("player_type_id") playerType:Int,@Query("tournament_id") id:Int, @Query("page")page:Int) : Flow<ApiResponse<BaseResponse<PlayerListResponse>>>

    @POST("user/create-team")
    fun createTeam(@Body requestBody: CreateTeamRequestModel): Flow<ApiResponse<BaseResponse<LoginResponse>>>

    @POST("user/update-team")
    fun updateTeam(@Body requestBody: UpdateTeamRequestModel): Flow<ApiResponse<BaseResponse<LoginResponse>>>

    @POST("user/update-team-payment")
    fun updateTeamPayment(@Body requestBody: UpdatePaymentRequest): Flow<ApiResponse<BaseResponse<LoginResponse>>>

    @GET("user/involved-tournament-list")
    fun getMyTournamentList(@Query("type") type:String) : Flow<ApiResponse<BaseResponse<TournamentListResponse>>>

    @GET("user/user-tournament-teams")
    fun getMyTeams(@Query("tournament_id") tournamentId:Int) : Flow<ApiResponse<MyMatchesList>>

    @GET("user/tournament-prize-distribution")
    fun getPrize(@Query("tournament_id") tournamentId:Int) : Flow<ApiResponse<MyMatchesList>>

    @GET("user/tournament-match-list")
    fun getMatches(@Query("tournament_id") id:Int, @Query("page")page:Int = 1) : Flow<ApiResponse<BaseResponse<MatchListResponse>>>

    @GET("user/tournament-stats")
    fun getStats(@Query("tournament_id") id:Int, @Query("page")page:Int = 1) : Flow<ApiResponse<BaseResponse<StatsListResponse>>>

    @GET("user/tournament-match-stats")
    fun getMatchStats(@Query("match_id") id:Int, @Query("page")page:Int = 1) : Flow<ApiResponse<BaseResponse<StatsListResponse>>>

    @GET("user/tournament-match-scorecard")
    fun getScoreCard(@Query("tournament_id") id:Int, @Query("match_id")matchId:Int, @Query("team_id")teamId:Int) : Flow<ApiResponse<ScorecardResponse>>
}
