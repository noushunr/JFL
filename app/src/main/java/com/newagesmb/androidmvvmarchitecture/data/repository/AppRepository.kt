package com.newagesmb.androidmvvmarchitecture.data.repository

import com.newagesmb.androidmvvmarchitecture.data.local.preferences.AppPreferences
import com.newagesmb.androidmvvmarchitecture.data.local.preferences.DataStoreManager
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
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.AppApis
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.BaseResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiResponse
import com.newagesmb.androidmvvmarchitecture.di.ApplicationScope
import com.newagesmb.androidmvvmarchitecture.di.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val appApis: AppApis,
    private val preferences: AppPreferences,
    private val dataStoreManager: DataStoreManager,
    @ApplicationScope private val externalScope: CoroutineScope,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) {

    fun saveBearerToken(value: String) {
        externalScope.launch(Dispatchers.IO) { dataStoreManager.setToken(value) }
    }


    fun saveUserName(value: String) {
        externalScope.launch(Dispatchers.IO) { dataStoreManager.setUserName(value) }
    }

    fun getUserName() = dataStoreManager.getName()

    fun setUserLoggedIn(value: Boolean) {
        // preferences.isUserLoggedIn = value
        externalScope.launch(Dispatchers.IO) { dataStoreManager.setUserLoggedIn(value) }
    }

    fun getLoginStatus() = dataStoreManager.isUserLoggedIn()


    suspend fun clearSession() {
        dataStoreManager.clearSession()
    }

    suspend fun saveSession(data: LoginModel) {
        dataStoreManager.saveSession(data)
    }

    fun getSession() =
        dataStoreManager.getSession().cancellable()

    fun login(loginRequestModel: LoginRequestModel): Flow<ApiResponse<BaseResponse<LoginResponse>>> {
        return appApis.login(loginRequestModel)
    }

    fun register(registerRequest: RegisterRequest): Flow<ApiResponse<BaseResponse<LoginResponse>>> {
        return appApis.register(registerRequest)
    }

    fun login(loginRequestModel: LoginRequest): Flow<ApiResponse<BaseResponse<LoginModel>>> {
        return appApis.login(loginRequestModel)
    }
    fun forgotPassword(requestModel: ForgotPasswordRequestModel): Flow<ApiResponse<BaseResponse<LoginResponse>>> {
        return appApis.forgotPassword(requestModel)
    }
    fun getUserProfile(): Flow<ApiResponse<BaseResponse<EditProfileModel>>> {
        return appApis.get_profile_details()
    }

    fun getTeamCreationCriteria(tournamentId : Int) : Flow<ApiResponse<BaseResponse<TeamCreationCriteria>>>{
        return appApis.getTeamCreationCriteria(tournamentId)
    }

    fun getTournamentList() : Flow<ApiResponse<BaseResponse<TournamentListResponse>>>{
        return appApis.getTournamentList()
    }
    fun getPlayerTypes(tournamentId: Int) : Flow<ApiResponse<PlayerTypeResponse>>{
        return appApis.getPlayerTypes(tournamentId)
    }
    fun getPlayersList(playerType : Int, tournamentId: Int) : Flow<ApiResponse<BaseResponse<PlayerListResponse>>>{
        return appApis.getPlayerListByTypes(playerType,tournamentId)
    }
    fun paginatePlayersList(playerType : Int, tournamentId: Int,page:Int) : Flow<ApiResponse<BaseResponse<PlayerListResponse>>>{
        return appApis.paginatePlayerList(playerType,tournamentId,page)
    }
    fun createTeam(createTeamRequestModel: CreateTeamRequestModel) : Flow<ApiResponse<BaseResponse<LoginResponse>>> {
        return appApis.createTeam(createTeamRequestModel)
    }
    fun updateTeam(updateTeamRequestModel: UpdateTeamRequestModel) : Flow<ApiResponse<BaseResponse<LoginResponse>>> {
        return appApis.updateTeam(updateTeamRequestModel)
    }
    fun updatePayment(updatePaymentRequest: UpdatePaymentRequest) : Flow<ApiResponse<BaseResponse<LoginResponse>>> {
        return appApis.updateTeamPayment(updatePaymentRequest)
    }
    fun getMyTournamentList(type:String) : Flow<ApiResponse<BaseResponse<TournamentListResponse>>>{
        return appApis.getMyTournamentList(type)
    }
    fun getMyMatches(tournamentId: Int) : Flow<ApiResponse<MyMatchesList>>{
        return appApis.getMyTeams(tournamentId)
    }
    fun getPrizes(tournamentId: Int) : Flow<ApiResponse<MyMatchesList>>{
        return appApis.getPrize(tournamentId)
    }
    fun getMatches(tournamentId: Int,page:Int) : Flow<ApiResponse<BaseResponse<MatchListResponse>>>{
        return appApis.getMatches(tournamentId,page)
    }
    fun getStats(tournamentId: Int,page:Int) : Flow<ApiResponse<BaseResponse<StatsListResponse>>>{
        return appApis.getStats(tournamentId,page)
    }
    fun getMatchStats(matchId: Int,page:Int) : Flow<ApiResponse<BaseResponse<StatsListResponse>>>{
        return appApis.getMatchStats(matchId,page)
    }
    fun getScorecard(tournamentId: Int,matchId:Int, teamId:Int) : Flow<ApiResponse<ScorecardResponse>>{
        return appApis.getScoreCard(tournamentId,matchId, teamId)
    }
}
