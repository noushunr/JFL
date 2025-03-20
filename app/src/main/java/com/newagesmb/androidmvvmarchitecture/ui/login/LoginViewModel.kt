package com.newagesmb.androidmvvmarchitecture.ui.login

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginModel
import com.newagesmb.androidmvvmarchitecture.data.model.response.LoginResponse
import com.newagesmb.androidmvvmarchitecture.data.remote.apis.*
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository
import com.newagesmb.androidmvvmarchitecture.di.ApplicationScope
import com.newagesmb.androidmvvmarchitecture.di.MainDispatcher
import com.newagesmb.androidmvvmarchitecture.utils.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository, val resources: Resources
) : ViewModel() {

    var usernameErrorMessage = MutableStateFlow<String>("")

    var username = MutableStateFlow("")
//    var password = MutableStateFlow("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailErrorMessage by mutableStateOf("")
    var passwordErrorMessage by mutableStateOf("")


    private val _loginApiState = mutableEventFlow<Resource<BaseResponse<LoginResponse>>>()
    val loginApiState get() = _loginApiState.asSharedFlow()

    private val _logginState = mutableEventFlow<Boolean>()
    val isLoggedIn get() = _logginState.asSharedFlow()
    var isLogged by mutableStateOf(false)


    private val _loginState = MutableStateFlow<Resource<BaseResponse<LoginResponse>>>(Resource.Loading())
    val loginState: StateFlow<Resource<BaseResponse<LoginResponse>>> = _loginState

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus(){
         viewModelScope.launch {
             repository.getLoginStatus().collect {
                 isLogged = true
                 _logginState.tryEmit(it)
             }
         }
     }

    fun login() {

        viewModelScope.launch {
            emailErrorMessage = ""
            passwordErrorMessage = ""
            Log.d("YYY","login called in viewModel")
            if (validateInputs()) {
                var loginRequestModel = LoginRequestModel(username = email, password = password)
                var loginRequest=LoginRequest(email,password)
//                repository.login(loginRequest).onStart {
//                    _loginApiState.tryEmit(Resource.Loading())
//                }
                repository.login(loginRequestModel).onStart {
                    _loginState.tryEmit(Resource.StartLoading())
                }
                    .collectLatest { it ->

                        when (it) {
                            is ApiEmptyResponse -> {}
                            is ApiErrorResponse -> {

                                _loginState.tryEmit(Resource.Error(it.errorMessage,it.errorType))
                            }
                            is ApiSuccessResponse -> {

                                if (it.data != null) {
                                    Log.d("YYY","response ${it.data.data}")
                                    /**     saving the user data     */
//                                   it.data.data?.let { it1 -> repository.saveSession(it1)
                                       repository.setUserLoggedIn(true)
//                                   }

                                    it.data.data?.token?.let {token->
                                        repository.saveBearerToken(token)
                                        repository.saveUserName(it.data.data?.name!!)
                                    }


                                    _loginState.tryEmit(Resource.Success(it.data))
                                   // repository.setUserLoggedIn(true)
                                   // repository.saveBearerToken(it.data.data?.token!!)

                                } else {
                                    _loginState.tryEmit(Resource.Error(it.data?.message,ErrorType.OtherError))
                                }
                            }
                            else -> {

                            }
                        }
                    }
            }


        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        var isValid = false
        when {
            email.isEmpty() -> {
                usernameErrorMessage.value = resources.getString(R.string.empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                usernameErrorMessage.value = resources.getString(R.string.invalid_email)
            }
            password.isEmpty() -> passwordErrorMessage =
                resources.getString(R.string.empty_password)
            else -> {
                emailErrorMessage = ""
                passwordErrorMessage = ""
                isValid = true
            }

        }
        return isValid
    }

    private fun validateInputs(): Boolean {
        var isValid = false
        when {

            email.isBlank() -> {
                emailErrorMessage = resources.getString(R.string.empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailErrorMessage = resources.getString(R.string.invalid_email)
            }
            password.isEmpty() -> passwordErrorMessage =
                resources.getString(R.string.empty_password)
            else -> {
                emailErrorMessage = ""
                passwordErrorMessage = ""
                isValid = true
            }

        }
        return isValid
    }

}