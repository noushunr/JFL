package com.newagesmb.androidmvvmarchitecture.ui.register

import android.content.Context
import android.util.Log
import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequest
import com.newagesmb.androidmvvmarchitecture.data.model.request.LoginRequestModel
import com.newagesmb.androidmvvmarchitecture.data.model.request.RegisterRequest
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
class RegisterViewModel  @Inject constructor(
    private val repository: AppRepository, val context: Context
) : ViewModel() {
    // TODO: Implement the ViewModel
    var usernameErrorMessage = MutableStateFlow<String>("")
//    var mobileErrorMessage = MutableStateFlow<String>("")
    var firstNameErrorMessage = MutableStateFlow<String>("")
    var lastNameErrorMessage = MutableStateFlow<String>("")
//    var passwordErrorMessage = MutableStateFlow<String>("")

//    val email = MutableStateFlow("")
    val mobile = MutableStateFlow("")
    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
//    val password = MutableStateFlow("")

     val registerState = mutableEventFlow<Boolean>()

    var name by mutableStateOf("")
    var mobNo by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var emailErrorMessage by mutableStateOf("")
    var passwordErrorMessage by mutableStateOf("")
    var nameErrorMessage by mutableStateOf("")
    var mobileErrorMessage by mutableStateOf("")
    var confirmPasswordErrorMessage by mutableStateOf("")

    private val _loginState = MutableStateFlow<Resource<BaseResponse<LoginResponse>>>(Resource.Loading())
    val loginState: StateFlow<Resource<BaseResponse<LoginResponse>>> = _loginState

    fun register() {

        viewModelScope.launch {
            emailErrorMessage = ""
            mobileErrorMessage = ""
            nameErrorMessage = ""
            passwordErrorMessage = ""
            confirmPasswordErrorMessage = ""
            if (validateInputs()) {
                val registerRequest = RegisterRequest(username = email, name = name, mobileNumber = mobNo, password = password, passwordConfirmation = confirmPassword)
//
                repository.register(registerRequest).onStart {
                    _loginState.tryEmit(Resource.StartLoading())
                }
                    .collectLatest {

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
//                                    repository.setUserLoggedIn(true)
//                                   }


                                    _loginState.tryEmit(Resource.Success(it.data))
                                    // repository.setUserLoggedIn(true)
                                    // repository.saveBearerToken(it.data.data?.token!!)

                                } else {
                                    _loginState.tryEmit(Resource.Error(it.data?.message, ErrorType.OtherError))
                                }
                            }
                            else -> {

                            }
                        }
                    }
            }


        }
    }

    fun validateInputs() : Boolean {

        var isValid = false
        when {
            name.isEmpty() -> {
                nameErrorMessage = context.getString(R.string.empty_first_name)
            }
            mobNo.isEmpty() -> {
                nameErrorMessage = ""
                mobileErrorMessage = context.getString(R.string.empty_phone)
            }
            !Patterns.PHONE.matcher(mobNo).matches() -> {
                nameErrorMessage = ""
                mobileErrorMessage = context.getString(R.string.invalid_phone)
            }
            email.isEmpty() -> {
                nameErrorMessage = ""
                mobileErrorMessage =""
                emailErrorMessage = context.getString(R.string.empty_email)
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                nameErrorMessage = ""
                mobileErrorMessage =""
                emailErrorMessage = context.getString(R.string.invalid_email)
            }
            password.isEmpty() -> {
                emailErrorMessage = ""
                mobileErrorMessage = ""
                nameErrorMessage = ""
                passwordErrorMessage = context.getString(R.string.empty_password)
            }
            password.length<6 -> {
                emailErrorMessage = ""
                mobileErrorMessage = ""
                nameErrorMessage = ""
                passwordErrorMessage = context.getString(R.string.alert_password_strength)
            }
            confirmPassword.isEmpty() -> {
                emailErrorMessage = ""
                mobileErrorMessage = ""
                nameErrorMessage = ""
                passwordErrorMessage = ""
                confirmPasswordErrorMessage = context.getString(R.string.empty_password)
            }
            confirmPassword != password -> {
                emailErrorMessage = ""
                mobileErrorMessage = ""
                firstNameErrorMessage.value = ""
                lastNameErrorMessage.value = ""
                confirmPasswordErrorMessage = context.getString(R.string.password_mismatch)
            }
//            password.isEmpty() -> passwordErrorMessage.value =
//                context.getString(R.string.empty_password)
            else -> {
                emailErrorMessage = ""
                mobileErrorMessage = ""
                nameErrorMessage = ""
                passwordErrorMessage = ""
                confirmPasswordErrorMessage = ""

                isValid = true
            }


        }
        return isValid
//        viewModelScope.launch {
//            registerState.emit(isValid)
//        }
    }

}