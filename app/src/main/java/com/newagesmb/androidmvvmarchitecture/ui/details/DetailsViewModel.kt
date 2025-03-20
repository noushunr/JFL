package com.newagesmb.androidmvvmarchitecture.ui.details

import android.os.Build
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagesmb.androidmvvmarchitecture.data.model.response.EditProfileModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.MatchListResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.MyMatchesList
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayerTypeResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.ScorecardResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.StatsListResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamCreationCriteria
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentListResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.*
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository
import com.newagesmb.androidmvvmarchitecture.utils.NetworkUtils
import com.newagesmb.androidmvvmarchitecture.utils.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {


    private val _userSession = mutableEventFlow<LoginModel>()
    val userSession get() = _userSession.asSharedFlow()

    private val _myTournamentListState = MutableStateFlow<Resource<MyMatchesList>>(Resource.Loading())
    val myTournamentLists: StateFlow<Resource<MyMatchesList>> = _myTournamentListState

    private val _matchesListState = MutableStateFlow<Resource<BaseResponse<MatchListResponse>>>(Resource.Loading())
    val matchesLists: StateFlow<Resource<BaseResponse<MatchListResponse>>> = _matchesListState

    private val _matchesStatsState = MutableStateFlow<Resource<BaseResponse<MatchListResponse>>>(Resource.Loading())
    val matchesStatsLists: StateFlow<Resource<BaseResponse<MatchListResponse>>> = _matchesStatsState

    private val _statsListState = MutableStateFlow<Resource<BaseResponse<StatsListResponse>>>(Resource.Loading())
    val statsListState: StateFlow<Resource<BaseResponse<StatsListResponse>>> = _statsListState

//    private val _scoreCardState = MutableStateFlow<Resource<ScorecardResponse>>(Resource.Loading())
//    val scoreCardState: StateFlow<Resource<ScorecardResponse>> = _scoreCardState

    private val _scoreCardState = MutableStateFlow<Map<Int, Resource<ScorecardResponse>>>(emptyMap())
    val scoreCardState: StateFlow<Map<Int, Resource<ScorecardResponse>>> = _scoreCardState

//    private val _scoreCardState = mutableStateMapOf<Int, Resource<MatchScoreCard>>() // Cache per teamId
//    val scoreCardState = _scoreCardState.asStateMap()

    private val _scoreCardStateB = MutableStateFlow<Resource<ScorecardResponse>>(Resource.Loading())
    val scoreCardStateB: StateFlow<Resource<ScorecardResponse>> = _scoreCardStateB

    private val _profileApiState =
        mutableEventFlow<Resource<BaseResponse<EditProfileModel>>>()
    val profileState
        get() = _profileApiState.asSharedFlow()
    private var sessionFlowJob: Job? = null
    var userName by mutableStateOf("")

    var currentPages by mutableStateOf(1)
    var totalPages by mutableStateOf(1)

    var currentPageMatch by mutableStateOf(1)
    var totalPageMatch by mutableStateOf(1)

    init {
        viewModelScope.launch {
            repository.getUserName().collectLatest {
                userName = it!!
            }
        }

    }

    fun getSessionDetails() {

        sessionFlowJob = viewModelScope.launch {
            repository.getSession()
                .collectLatest {
                    if (it!=null)
                        _userSession.tryEmit(it)
                }


        }
    }

    fun getMyMatches(tournamentId:Int){
        viewModelScope.launch {
            repository.getMyMatches(tournamentId).onStart {
                _myTournamentListState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _myTournamentListState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data}")


                            _myTournamentListState.tryEmit(Resource.Success(it.data))


                        } else {
//                            _myTournamentListState.tryEmit(Resource.Error(it.data?., ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

    fun getMatches(tournamentId:Int,page:Int){
        viewModelScope.launch {
            repository.getMatches(tournamentId,page).onStart {
                _matchesListState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _matchesListState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data}")
                            currentPages = it.data.data?.currentPage?.toInt()?:1
                            totalPages = it.data.data?.totalPages?:0
                            val updatedMatches = it.data.data?.tournamentMatches?.map { match ->
                                match.copy(isEditEnabled = checkEditAvailability(match.matchDate!!, match.matchTime!!))
                            }

                            // Wrap updated data back into response
                            val updatedResponse = BaseResponse(
                                data = it.data.data?.copy(tournamentMatches = updatedMatches!!)
                            )
                            _matchesListState.tryEmit(Resource.Success(it.data))

                        } else {
//                            _myTournamentListState.tryEmit(Resource.Error(it.data?., ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

    fun getMatchesForStats(tournamentId:Int,page:Int){
        viewModelScope.launch {
            repository.getMatches(tournamentId,page).onStart {
                _matchesStatsState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _matchesStatsState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data}")
                            currentPageMatch = it.data.data?.currentPage?.toInt()?:1
                            totalPageMatch = it.data.data?.totalPages?:0
                            val updatedMatches = it.data.data?.tournamentMatches?.map { match ->
                                match.copy(isEditEnabled = checkEditAvailability(match.matchDate!!, match.matchTime!!))
                            }

                            // Wrap updated data back into response
                            val updatedResponse = BaseResponse(
                                data = it.data.data?.copy(tournamentMatches = updatedMatches!!)
                            )
                            _matchesStatsState.tryEmit(Resource.Success(updatedResponse))


                        } else {
//                            _myTournamentListState.tryEmit(Resource.Error(it.data?., ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }
    fun getStats(tournamentId:Int,page:Int){
        viewModelScope.launch {
            repository.getStats(tournamentId,page).onStart {
                _statsListState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _statsListState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data}")
                            currentPages = it.data.data?.currentPage?.toInt()?:1
                            totalPages = it.data.data?.totalPages?:0
                            _statsListState.tryEmit(Resource.Success(it.data))


                        } else {
//                            _myTournamentListState.tryEmit(Resource.Error(it.data?., ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

    fun getMatchStats(matchId:Int,page:Int){
        viewModelScope.launch {
            repository.getMatchStats(matchId,page).onStart {
                _statsListState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _statsListState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data}")
                            currentPages = it.data.data?.currentPage?.toInt()?:1
                            totalPages = it.data.data?.totalPages?:0
                            _statsListState.tryEmit(Resource.Success(it.data))


                        } else {
//                            _myTournamentListState.tryEmit(Resource.Error(it.data?., ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

//    fun getScoreCard(tournamentId:Int,matchId:Int, teamId:Int){
//        viewModelScope.launch {
//            repository.getScorecard(tournamentId,matchId, teamId).onStart {
//                _scoreCardState.tryEmit(Resource.StartLoading())
//            }.collectLatest {
//                when (it) {
//                    is ApiEmptyResponse -> {}
//                    is ApiErrorResponse -> {
//
//                        _scoreCardState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
//                    }
//
//                    is ApiSuccessResponse -> {
//                        if (it.data != null) {
//                            Log.d("YYY","response ${it.data}")
//                            currentPages = it.data.currentPage?.toInt()?:1
//                            totalPages = it.data.totalPages?:0
//                            _scoreCardState.tryEmit(Resource.Success(it.data))
//
//
//                        } else {
////                            _myTournamentListState.tryEmit(Resource.Error(it.data?., ErrorType.OtherError))
//                        }
//                    }
//                    else-> {
//
//                    }
//                }
//            }
//        }
//    }

    fun getScoreCard(tournamentId: Int, matchId: Int, teamId: Int) {
        if (_scoreCardState.value.containsKey(teamId)) {
            // Already fetched, no need to call API again
            return
        }

        viewModelScope.launch {
            repository.getScorecard(tournamentId, matchId, teamId).onStart {
                _scoreCardState.update { it + (teamId to Resource.StartLoading()) }
            }.collectLatest { response ->
                when (response) {
                    is ApiErrorResponse -> {
                        _scoreCardState.update { it + (teamId to Resource.Error(response.errorMessage, response.errorType)) }
                    }

                    is ApiSuccessResponse -> {
                        response.data?.let { data ->
                            Log.d("YYY", "response for teamId $teamId: $data")
                            _scoreCardState.update { it + (teamId to Resource.Success(data)) }
                        }
                    }

                    else -> { /* Handle other cases if needed */ }
                }
            }
        }
    }

    public fun checkEditAvailability(matchDate: String, matchTime: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            // Parse match start time
            return try {
                val matchDateTime = LocalDateTime.parse("$matchDate $matchTime", dateTimeFormatter)

                // Define the edit window (60 minutes before match start)
                val editStartTime = matchDateTime.minusMinutes(30)
                val currentTime = LocalDateTime.now(ZoneId.systemDefault())

                // Enable edit only in this 30-minute window
                currentTime.isAfter(editStartTime) && currentTime.isBefore(matchDateTime)
            } catch (e: Exception) {
                false // In case of parsing errors
            }
        } else {
            return false
        }

    }

    fun checkMyTeamEditAvailability(matchDate: String, matchTime: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            // Parse match start time
            return try {
                val matchDateTime = LocalDateTime.parse("$matchDate $matchTime", dateTimeFormatter)
                val editStartTime = matchDateTime.minusMinutes(30)

                val currentTime = LocalDateTime.now(ZoneId.systemDefault())

                Log.d("EditAvailability","${currentTime.isBefore(editStartTime)}")
                // Enable edit before the match start time
                currentTime.isBefore(editStartTime)
            } catch (e: Exception) {
                false // Handle parsing errors
            }
        } else {
            return false
        }

    }
}