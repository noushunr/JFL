package com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team

import android.content.res.Resources
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagesmb.androidmvvmarchitecture.data.model.request.CreateTeamRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.request.UpdatePaymentRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.UpdateTeamRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.MyMatchesList
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerListResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerPositions
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerTypeResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerTypes
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayersList
import com.newagesmb.androidmvvmarchitecture.data.model.response.SelectedTeams
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamCreationCriteria
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamPlayer
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentPrizeMoney
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiEmptyResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiErrorResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiSuccessResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.BaseResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ErrorType
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

//
// Created by Noushad on 03-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@HiltViewModel
class JoinContestViewModel @Inject constructor(
    private val repository: AppRepository, val resources: Resources
) : ViewModel() {
    private val _teamCreationState = MutableStateFlow<Resource<BaseResponse<TeamCreationCriteria>>>(Resource.Loading())
    val teamCreationCriteria: StateFlow<Resource<BaseResponse<TeamCreationCriteria>>> = _teamCreationState

    private val _playerTypes = MutableStateFlow<Resource<PlayerTypeResponse>>(Resource.Loading())
    val playerTypes: StateFlow<Resource<PlayerTypeResponse>> = _playerTypes

    private val _prizeMoney = MutableStateFlow<Resource<MyMatchesList>>(Resource.Loading())
    val prizeMoney: StateFlow<Resource<MyMatchesList>> = _prizeMoney

    private val _playerList = MutableStateFlow<Resource<BaseResponse<PlayerListResponse>>>(Resource.Loading())
    val playerList: StateFlow<Resource<BaseResponse<PlayerListResponse>>> = _playerList

    private val _createTeam = MutableStateFlow<Resource<BaseResponse<LoginResponse>>>(Resource.Loading())
    private val _updateTeam = MutableStateFlow<Resource<BaseResponse<LoginResponse>>>(Resource.Loading())
    private val _updatePayment = MutableStateFlow<Resource<BaseResponse<LoginResponse>>>(Resource.Loading())
    val updateTeam: StateFlow<Resource<BaseResponse<LoginResponse>>> = _updateTeam
    val createTeam: StateFlow<Resource<BaseResponse<LoginResponse>>> = _updatePayment

    var isLoading by mutableStateOf(false)

    var maxPlayerLimit by mutableStateOf(0)
    var foriegnPlayerLimit by mutableStateOf(0)
    var maxPlayerLimitByTeam by mutableStateOf(1)
    var minPlayerLimitByTeam by mutableStateOf(0)
    var totalTeamNo by mutableStateOf(0)
    var teamTypeLabel = mutableStateMapOf<Int, String>()
    var isCriteriaSatisfied by mutableStateOf(false)
    var isCaptainsSelected by mutableStateOf(false)
    var playerTypesList by mutableStateOf(mutableListOf<PlayerTypes>())
    var maxReachedTeamIds = mutableStateListOf<Int>()
    var selectedPlayersList = mutableStateListOf<Players>()

    var selectedTeams = mutableStateListOf<SelectedTeams>()
    var playerTypeCriteris = mutableStateListOf<PlayerPositions>()
    val playerDataCache = mutableStateMapOf<Int, MutableList<PlayersList>>() // Cache for tab data
    val playersList = MutableStateFlow<Resource<List<PlayersList>>>(Resource.Loading())
    val currentPagesByType = mutableStateMapOf<Int, String>()
    val totalPagesByType = mutableStateMapOf<Int, Int>()

    var tournamentId by mutableStateOf(0)
    var tournamentType by mutableStateOf(0)
    var tournamentTeamId by mutableStateOf(0)
    var teamList by mutableStateOf(mutableListOf<TeamPlayer>())

    var prizeList by mutableStateOf(listOf<TournamentPrizeMoney>())
    var tournamentName  by mutableStateOf("")
    var price  by mutableStateOf("")
    var totalPrizeMoney  by mutableStateOf("")

    var removedId = mutableStateListOf<Int>()

    val playerMaxReached = mutableStateMapOf<Int, Boolean>() // Cache for tab data
    //    val playerTypesList = mutableStateListOf<String>()
    fun getTournamentPrize(tournamentId: Int){
        viewModelScope.launch {
            repository.getPrizes(tournamentId).onStart {

            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _playerTypes.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {

                            prizeList = it.data.tournament?.tournamentPrizeMoney!!
                            tournamentName = it.data.tournament?.tournamentName!!
                            totalPrizeMoney = it.data.tournament?.totalPrizeMoney!!
                            price = it.data.tournament?.price!!
                            _prizeMoney.tryEmit(Resource.Success(it.data))

                            // repository.setUserLoggedIn(true)
                            // repository.saveBearerToken(it.data.data?.token!!)

                        } else {
                            _prizeMoney.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }

    }
    fun getPlayerTypes(tournamentId: Int){
        if (playerTypesList.isEmpty()){
            viewModelScope.launch {
                repository.getPlayerTypes(tournamentId).onStart {

                }.collectLatest {
                    when (it) {
                        is ApiEmptyResponse -> {}
                        is ApiErrorResponse -> {

                            _playerTypes.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                        }

                        is ApiSuccessResponse -> {
                            if (it.data != null) {

                                _playerTypes.tryEmit(Resource.Success(it.data))
                                playerTypesList = it.data.playerTypes.toMutableList()

                                // repository.setUserLoggedIn(true)
                                // repository.saveBearerToken(it.data.data?.token!!)

                            } else {
                                _playerTypes.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                            }
                        }
                        else-> {

                        }
                    }
                }
            }
        }

    }
    fun getTeamCreationCriteria(tournamentId : Int){
        viewModelScope.launch {
            repository.getTeamCreationCriteria(tournamentId).onStart {
                _teamCreationState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _teamCreationState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data.data}")

                            _teamCreationState.tryEmit(Resource.Success(it.data))
                            if (it.data.data?.playerCriteria!=null){

                                maxPlayerLimit = it.data.data?.playerCriteria?.playerLimit!!
                                foriegnPlayerLimit = it.data.data?.playerCriteria?.foreignerPlayerLimit!!
                                maxPlayerLimitByTeam = it.data.data?.playerCriteria?.maxPlayerLimitPerTeam!!
                                minPlayerLimitByTeam = it.data.data?.playerCriteria?.minPlayerLimitPerTeam!!
                                playerTypeCriteris.clear()
                                playerTypeCriteris.addAll(it.data?.data?.playerPositionCriteria!!)
                                totalTeamNo = it.data.data?.totalTeams!!
                            }
                            // repository.setUserLoggedIn(true)
                            // repository.saveBearerToken(it.data.data?.token!!)

                        } else {
                            _teamCreationState.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

    fun getPlayersList(playerTypeId : Int, tournamentId: Int){
//        teamTypeLabel = playerTypeLabel(playerTypeId)
        if (playerDataCache.containsKey(playerTypeId)) {
            // Data already cached, no need to fetch again
            _playerList.tryEmit(Resource.Success(BaseResponse(data = PlayerListResponse(playersList = playerDataCache[playerTypeId]!!), message = "", messageCode = "", status = 1, success = true)))
            return
        }
        viewModelScope.launch {
          repository.getPlayersList(playerTypeId,tournamentId).onStart {

          }.collectLatest {
              when (it) {
                  is ApiEmptyResponse -> {}
                  is ApiErrorResponse -> {

                      playersList.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                  }

                  is ApiSuccessResponse -> {
                      it.data.let { data ->
                          val playersList = data?.data?.playersList?.toMutableList() ?: mutableListOf()
                          // Cache the data for this player type
                          playerDataCache[playerTypeId] = playersList
                          teamTypeLabel[playerTypeId] = getPosHeader(playerTypeId)
                          currentPagesByType[playerTypeId] = data?.data?.currentPage!!
                          totalPagesByType[playerTypeId] = data?.data?.totalPages!!
                          populateSelectedPlayers(playerTypeId,playersList)
                          _playerList.tryEmit(Resource.Success(data))
                      }
                  }
                  else-> {

                  }
              }
          }
        }
    }

    private fun populateSelectedPlayers(playerTypeId: Int, playersList: MutableList<PlayersList>){
        if (teamList.isNotEmpty()){
            val playerIds = teamList.map { it.playerId }.toSet()
            val selectedPlayers = playerDataCache[playerTypeId]?.filter {
                it.playerId in playerIds
            }
// Get teamCode for matching IDs from list2

            selectedPlayers?.forEach { playerList->
                val captain = teamList.find { playerList.playerId == it.playerId && it.isCaptain ==1 }
                val viceCaptain = teamList.find { playerList.playerId == it.playerId && it.isViceCaptain ==1 }
                val updatedPlayer = playerList.copy(
                    player = playerList.player?.copy(
                        isSelected = true,
                        posId = playerTypeId,
                        teamCode =  playerList.team?.teamCode?:"",
                        isCaptain = captain?.isCaptain?:0,
                        isViceCaptain =viceCaptain?.isViceCaptain?:0
                    )
                )
                addOrRemovePlayer(updatedPlayer.player!!,playerList.teamId)
                val index = playerDataCache[playerTypeId]?.indexOfFirst { it.playerId == playerList.playerId }
                if (index!=null && index!=-1){
                    playersList[index] = updatedPlayer
//                                      playersList.toMutableList()[index] = updatedPlayer

                    // Update the ViewModel state
                    updatePlayerList(playerTypeId, playersList)
                }

            }

//            setCaptains()
        }
    }

    fun paginatePlayerList(playerTypeId : Int, tournamentId: Int,page:Int){
//        teamTypeLabel = playerTypeLabel(playerTypeId)
//        if (playerDataCache.containsKey(playerTypeId)) {
//            // Data already cached, no need to fetch again
//            _playerList.tryEmit(Resource.Success(BaseResponse(data = PlayerListResponse(playersList = playerDataCache[playerTypeId]!!), message = "", messageCode = "", status = 1, success = true)))
//            return
//        }
        viewModelScope.launch {
            repository.paginatePlayersList(playerTypeId,tournamentId,page).onStart {

            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        playersList.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        it.data.let { data ->
                            val playersList = data?.data?.playersList ?: emptyList()
                            val currentList = playerDataCache[playerTypeId]?.toMutableList()
                            currentList?.addAll(playersList)
                            // Cache the data for this player type
                            playerDataCache[playerTypeId] = currentList?.toMutableList()!!
//                            teamTypeLabel[playerTypeId] = getPosHeader(playerTypeId)
                            currentPagesByType[playerTypeId] = data?.data?.currentPage!!
                            populateSelectedPlayers(playerTypeId,currentList)
//                            totalPagesByType[playerTypeId] = data?.data?.totalPages!!
//                            _playerList.tryEmit(Resource.Success(data))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }
    fun playerTypeLabel(playerTypeId: Int) : Int{
        var playerType = ""
        var posMaxLimit = Int.MAX_VALUE
        playerTypesList.forEach {
            if (playerTypeId == it.id)
                playerType = it.role!!
        }
        playerTypeCriteris.forEach {
            if (playerTypeId == it.positionId){
                posMaxLimit = it.positionLimit
                playerType = "Pick 1-${it.positionLimit} $playerType "
            }
        }
        return posMaxLimit
    }
    fun getPosHeader(playerTypeId: Int) : String{
        var playerType = playerTypesList?.find { it.id == playerTypeId }?.role?:""
//        playerTypesList.forEach {
//            if (playerTypeId == it.id)
//                playerType = it.role!!
//        }
        playerTypeCriteris.forEach {
            if (playerTypeId == it.positionId){
                playerType = "Pick 1-${it.positionLimit} $playerType"
            }
        }
        return playerType
    }
    fun updatePlayerList(playerTypeId: Int, updatedList: List<PlayersList>) {
        playerDataCache[playerTypeId] = updatedList.toMutableList()

    }

    fun addOrRemovePlayer(player : Players, teamId : Int){
        val selectedPlayerCount = selectedPlayersList.count { it.id == player.id }

        if (selectedPlayerCount==0){
            selectedPlayersList.add(player)
            maxReachedTeamIds.add(teamId)
            if (removedId.contains(player.id)){
                removedId.remove(player.id)
            }
        } else {
            val playerToRemove = selectedPlayersList.firstOrNull { it.id == player.id }
            if (playerToRemove != null) {
                if (teamList.isNotEmpty()){
                    teamList.find { it.playerId == playerToRemove.id }?.let {
                        removedId.add(playerToRemove.id)
                    }
//                    val teamPlayer = teamList.find { it.playerId == playerToRemove.id }
//                    if (teamPlayer!=null)
//                        removedId.add(playerToRemove.id)
                }
                selectedPlayersList.remove(playerToRemove)
                maxReachedTeamIds.remove(teamId)
            }
//            selectedPlayersList.forEachIndexed { index, players ->
//                if (players.id == player.id){
//                    selectedPlayersList.removeAt(index)
//                    return@forEachIndexed
//                }
//            }
//            selectedPlayersList.remove(player)
//            maxReachedTeamIds.remove(teamId)
        }
        isCriteriaSatisfied = if (maxReachedTeamIds.distinct().size == totalTeamNo){
    //            var isMinAddedd = false
            selectedPlayersList.size == maxPlayerLimit
        } else {
            false
        }
        val playerCount = selectedPlayersList.filter { it.posId == player.posId }.size

//        playerTypesList.forEach {
//            if (player.posId == it.id){
//                it.roleCode = "${it.roleCode?.replace(Regex("\\(.*?\\)"), "")}($playerCount)"
//            }
//
//        }
        val index = playerTypesList.indexOfFirst { it.id == player.posId }

        val updatedPlayer = playerTypesList[index].copy(
            roleCode = "${playerTypesList[index].roleCode?.replace(Regex("\\(.*?\\)"), "")}($playerCount)", role = playerTypesList[index].role, id = playerTypesList[index].id)
        playerTypesList[index] = updatedPlayer
//        if (!maxReachedTeamIds.contains(teamId)){
//
//        }

        if (teamList.isNotEmpty())
            setCaptains()
    }
    fun setCaptains(){
        val hasCaptain = selectedPlayersList.any { it.isCaptain == 1 }
        val hasViceCaptain = selectedPlayersList.any { it.isViceCaptain == 1 }
        isCaptainsSelected = hasCaptain && hasViceCaptain
    }

    fun createTeam(){
        val createTeamRequestModel = CreateTeamRequestModel(tournamentId = tournamentId, playersList = selectedPlayersList)
        viewModelScope.launch {
            repository.createTeam(createTeamRequestModel).onStart {
                isLoading = true
                _createTeam.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _createTeam.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {

                            _createTeam.tryEmit(Resource.Success(it.data))
                            updatePayment(it.data.userTournamentTeamId)

                        } else {
                            _createTeam.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

    fun updateTeam(){
        val filteredChangedItems = selectedPlayersList.filter { newPlayer ->
            val existingPlayer = teamList.find { it.playerId == newPlayer.id }

            existingPlayer == null ||
                    existingPlayer.isCaptain != newPlayer.isCaptain ||
                    existingPlayer.isViceCaptain != newPlayer.isViceCaptain
        }
        val changedTeams = selectedPlayersList.filter { newPlayer ->
            val existingPlayer = teamList.find { it.playerId == newPlayer.id }

            existingPlayer != null && ( // Ensure player exists in teamList
            existingPlayer.isCaptain != newPlayer.isCaptain ||
                    existingPlayer.isViceCaptain != newPlayer.isViceCaptain
            )
        }
        changedTeams.forEach {
            removedId.add(it.id)
        }
//        if (filteredChangedItems.isNotEmpty()){
//
//        }
        val newPlayerList = selectedPlayersList.filterNot { item2 -> teamList.any { it.playerId == item2.id } }

        if (filteredChangedItems.isNotEmpty()){
            val newPlayerIds = filteredChangedItems.map { it.id }
            val newPlayerName = filteredChangedItems.map { it.playerName }
            val newPlayerPos = filteredChangedItems.map { it.posId }
            val newPlayerCaptain = filteredChangedItems.map { it.isCaptain }
            val newPlayerViceCaptain = filteredChangedItems.map { it.isViceCaptain }
            val updateTeamRequestModel = UpdateTeamRequestModel(
                tournamentId = tournamentId,tournamentTeamId = tournamentTeamId, tournamentTeam = removedId, newPlayer = newPlayerIds,
                newPlayerName = newPlayerName, newPlayerPosition =  newPlayerPos, newPlayerCaptain = newPlayerCaptain, newPlayerViceCaptain = newPlayerViceCaptain)

//            val createTeamRequestModel = CreateTeamRequestModel(tournamentId = tournamentId, playersList = selectedPlayersList)
            viewModelScope.launch {
                repository.updateTeam(updateTeamRequestModel).onStart {
                    isLoading = true
                    _updateTeam.tryEmit(Resource.StartLoading())
                }.collectLatest {
                    when (it) {
                        is ApiEmptyResponse -> {}
                        is ApiErrorResponse -> {

                            _updateTeam.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                        }

                        is ApiSuccessResponse -> {
                            if (it.data != null) {

                                _updateTeam.tryEmit(Resource.Success(it.data))

                            } else {
                                _updateTeam.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                            }
                        }
                        else-> {

                        }
                    }
                }
            }
        }
    }

    private fun updatePayment(userTournamentTeamId : Int){
        val updatePaymentRequest = UpdatePaymentRequest(tournamentId = userTournamentTeamId)
        viewModelScope.launch {
            repository.updatePayment(updatePaymentRequest).onStart {
                _createTeam.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _updatePayment.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {

                            _updatePayment.tryEmit(Resource.Success(it.data))

                        } else {
                            _updatePayment.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }
    fun getPositionName(posId:Int):String{
        return playerTypesList.find { it.id == posId }?.role ?: ""
    }
    fun getPositionCode(posId:Int):String{
        return playerTypesList.find { it.id == posId }?.roleCode ?: ""
    }

    fun getTeamCount(){
        val list : MutableList<SelectedTeams> = mutableListOf()
        selectedTeams.clear()
        selectedPlayersList.let {
            val groupedByTeam = it.groupBy { it.teamCode }
            val foreignersTeam = it.groupBy { it.isForeigner }
            groupedByTeam.forEach {
                val selectedTeam = SelectedTeams(it.key,it.value.size)
                selectedTeams.add(selectedTeam)
            }
            if (foreignersTeam.isNotEmpty()){
                val foreignPlayersCount = foreignersTeam[1]?.size ?: 0  // Count of foreign players
                val indianPlayersCount = foreignersTeam[0]?.size ?: 0   // Count of Indian players
                selectedTeams.add(SelectedTeams("Foreign Players", foreignPlayersCount))
                selectedTeams.add(SelectedTeams("Indian Players", indianPlayersCount))
            }

//            foreignersTeam.filter { it.key == 1 }.let {
//                val selectedTeam = SelectedTeams("Foreign Players",it.values.count())
//                selectedTeams.add(selectedTeam)
//            }
//            foreignersTeam.filter { it.key == 0 }.let {
//                val selectedTeam = SelectedTeams("Indian Players",it.values.count())
//                selectedTeams.add(selectedTeam)
//            }
        }
//        selectedTeams = list as SnapshotStateList<SelectedTeams>
    }
    fun clearData(){
        maxPlayerLimit = 0
        foriegnPlayerLimit = 0
        maxPlayerLimitByTeam = 1
        minPlayerLimitByTeam = 0
        totalTeamNo = 0
        isCriteriaSatisfied = false
        isCaptainsSelected = false
        teamTypeLabel.clear() // clear the map
        playerTypesList.clear() // clear the list
        maxReachedTeamIds.clear() // clear the list
        selectedPlayersList.clear() // clear the list
        selectedTeams.clear() // clear the list
        playerTypeCriteris.clear() // clear the list
        teamList.clear()
        removedId.clear()

        tournamentTeamId =0
        // Reset the cache map
        playerDataCache.clear()

        // Reset the players list
        playersList.value = Resource.Success(emptyList())
        _createTeam.value = Resource.Loading()
        _updatePayment.value = Resource.Loading()
        _updateTeam.value = Resource.Loading()

//        // Clear currentPagesByType and totalPagesByType maps
//        currentPagesByType.clear()
//        totalPagesByType.clear()
//
//        // Reset tournamentId
//        tournamentId = 0
    }

    fun getInfos():MutableList<String>{
        //The maximum number of players is 20.
        // Each team must have a minimum of 1 player and a maximum of 3.
        // The foreign player limit is 10.
        // The position limits are as follows: Batter - 5, Bowler - 5, Wicket-keeper - 5, and All-rounder - 5.
        val infoList : MutableList<String> = mutableListOf()
        infoList.add("The maximum number of players is $maxPlayerLimit")
        infoList.add("Each team must have a minimum of $minPlayerLimitByTeam player and maximum of $maxPlayerLimitByTeam")
        infoList.add("The foreign player limit is $foriegnPlayerLimit")
        infoList.add("The position limits are as follows:")
        playerTypeCriteris.forEach {
            val positionName = getPositionName(it.positionId)
            infoList.add("$positionName - ${it.positionLimit}")
        }
        return infoList
    }
}