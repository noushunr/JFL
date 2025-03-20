package com.newagesmb.androidmvvmarchitecture.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.newagesmb.androidmvvmarchitecture.data.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//
// Created by Noushad on 13-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {
    var navController : NavController?=null


}