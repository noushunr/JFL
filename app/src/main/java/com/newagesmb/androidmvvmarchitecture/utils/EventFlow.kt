package com.newagesmb.androidmvvmarchitecture.utils

import kotlinx.coroutines.flow.MutableSharedFlow

fun <T> mutableEventFlow(): MutableSharedFlow<T> {
    return MutableSharedFlow(
        replay = 0,
        extraBufferCapacity = 1
    )
}
