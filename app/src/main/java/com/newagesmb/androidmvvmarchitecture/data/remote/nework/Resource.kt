package com.newagesmb.androidmvvmarchitecture.data.remote.nework

import com.newagesmb.androidmvvmarchitecture.data.remote.apis.ErrorType

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    class StartLoading<T> : Resource<T>()

    data class Success<T>(
        val data: T?
    ) : Resource<T>()

    data class Error<T>(
        val errorMessage: String?,
        val errorType: ErrorType
    ) : Resource<T>()
}
