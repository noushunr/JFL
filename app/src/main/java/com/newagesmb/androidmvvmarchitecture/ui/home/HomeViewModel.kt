package com.newagesmb.androidmvvmarchitecture.ui.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagesmb.androidmvvmarchitecture.data.model.response.EditProfileModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginModel
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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {


    private val _userSession = mutableEventFlow<LoginModel>()
    val userSession get() = _userSession.asSharedFlow()

    private val _tournamentListState = MutableStateFlow<Resource<BaseResponse<TournamentListResponse>>>(Resource.Loading())
    val tournamentLists: StateFlow<Resource<BaseResponse<TournamentListResponse>>> = _tournamentListState

    private val _myTournamentListState = MutableStateFlow<Resource<BaseResponse<TournamentListResponse>>>(Resource.Loading())
    val myTournamentLists: StateFlow<Resource<BaseResponse<TournamentListResponse>>> = _myTournamentListState

    private val _profileApiState =
        mutableEventFlow<Resource<BaseResponse<EditProfileModel>>>()
    val profileState
        get() = _profileApiState.asSharedFlow()
    private var sessionFlowJob: Job? = null
    var userName by mutableStateOf("")
    init {
        viewModelScope.launch {
            repository.getUserName().collectLatest {
                if (it!=null)
                    userName = it
            }
        }

    }
    fun clearSession(logout:()->Unit) {

        viewModelScope.launch {
            sessionFlowJob?.cancel()
            repository.clearSession()

        }
        logout.invoke()
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

    fun getTournament(){
        viewModelScope.launch {
            repository.getTournamentList().onStart {
                _tournamentListState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _tournamentListState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data.data}")

                            _tournamentListState.tryEmit(Resource.Success(it.data))


                        } else {
                            _tournamentListState.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }
    fun getMyTournament(type:String){
        viewModelScope.launch {
            repository.getMyTournamentList(type).onStart {
                _myTournamentListState.tryEmit(Resource.StartLoading())
            }.collectLatest {
                when (it) {
                    is ApiEmptyResponse -> {}
                    is ApiErrorResponse -> {

                        _myTournamentListState.tryEmit(Resource.Error(it.errorMessage, it.errorType))
                    }

                    is ApiSuccessResponse -> {
                        if (it.data != null) {
                            Log.d("YYY","response ${it.data.data}")

                            _myTournamentListState.tryEmit(Resource.Success(it.data))


                        } else {
                            _myTournamentListState.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                        }
                    }
                    else-> {

                    }
                }
            }
        }
    }

}