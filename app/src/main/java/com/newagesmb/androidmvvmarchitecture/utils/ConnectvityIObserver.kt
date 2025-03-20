package com.newagesmb.androidmvvmarchitecture.utils

import kotlinx.coroutines.flow.Flow

interface ConnectvityIObserver {

    fun observe():Flow<Status>

    enum class Status{
        Avialable,Unavailable,Losing,Lost
    }
}