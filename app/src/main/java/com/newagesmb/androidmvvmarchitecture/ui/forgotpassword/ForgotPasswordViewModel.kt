package com.newagesmb.androidmvvmarchitecture.ui.forgotpassword

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.request.ForgotPasswordRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiEmptyResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiErrorResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ApiSuccessResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.BaseResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ErrorType
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository
import com.newagesmb.androidmvvmarchitecture.utils.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel  @Inject constructor(
    private val repository: AppRepository, val context: Context
) : ViewModel() {
    // TODO: Implement the ViewModel
    var emailErrorMessage  by mutableStateOf("")
    var email by mutableStateOf("")
    private val _forgotPasswordState = MutableStateFlow<Resource<BaseResponse<LoginResponse>>>(Resource.Loading())
    val forgotPasswordState: StateFlow<Resource<BaseResponse<LoginResponse>>> = _forgotPasswordState
    fun validateInputs() :Boolean{

        var isValid = false
        when {
            email.isEmpty() -> {
                emailErrorMessage = context.getString(R.string.empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailErrorMessage = context.getString(R.string.invalid_email)
            }

            else -> {
                emailErrorMessage = ""
                isValid = true
            }

        }

        return isValid
    }

    fun forgotPassword() {

        viewModelScope.launch {
            emailErrorMessage = ""
            if (validateInputs()) {
                var requestModel = ForgotPasswordRequestModel(email = email)
                repository.forgotPassword(requestModel).onStart {
                    _forgotPasswordState.tryEmit(Resource.StartLoading())
                }
                    .collectLatest { it ->

                        when (it) {
                            is ApiEmptyResponse -> {}
                            is ApiErrorResponse -> {

                                _forgotPasswordState.tryEmit(Resource.Error(it.errorMessage,it.errorType))
                            }
                            is ApiSuccessResponse -> {

                                if (it.data != null) {


                                    _forgotPasswordState.tryEmit(Resource.Success(it.data))
                                    // repository.setUserLoggedIn(true)
                                    // repository.saveBearerToken(it.data.data?.token!!)

                                } else {
                                    _forgotPasswordState.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                                }
                            }
                            else -> {

                            }
                        }
                    }
            }


        }
    }

}