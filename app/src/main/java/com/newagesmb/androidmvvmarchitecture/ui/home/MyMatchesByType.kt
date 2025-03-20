package com.newagesmb.androidmvvmarchitecture.ui.home

import android.app.Activity
import android.graphics.Paint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.Tournaments
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.Locale

//
// Created by Noushad on 10-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun MyMatchesByTypeScreen(navController : NavHostController, type:String) {
    val viewModel: HomeViewModel = hiltViewModel()
    val tournamentList = remember { mutableStateListOf<Tournaments>().apply { addAll(emptyList()) } }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
//    LaunchedEffect(Unit) {
//        snapshotFlow { type }
//            .distinctUntilChanged() // Prevents duplicate triggers
//            .collectLatest { selectedType ->
//                viewModel.getMyTournament(selectedType.lowercase())
//            }
//    }
    LaunchedEffect(type) {
        Log.d("MyMatchesByTypeScreen", "Fetching data for type: $type")

        launch {
            viewModel.getMyTournament(type.lowercase())
            viewModel.myTournamentLists.collectLatest {
                when (it) {
                    is Resource.Loading -> {
                        isLoading = true
                        // Show loading state
                    }

                    is Resource.StartLoading -> {

                        isLoading = true
                    }

                    is Resource.Success -> {

                        isLoading = false
                        tournamentList.clear()
                        // Handle success state
                        if (!it?.data?.data?.tournaments.isNullOrEmpty()) {

                            tournamentList.apply {
                                addAll(it.data?.data?.tournaments!!)
                            }
                        }


                    }

                    is Resource.Error -> {
                        isLoading = false
                        context.showToast(it?.errorMessage)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFE6E6E6))


    ) {
        Column(

            modifier = Modifier
                .fillMaxSize()
                , verticalArrangement = Arrangement.Center
        ) {
            if (tournamentList.isNullOrEmpty()){
                Box (modifier = Modifier.fillMaxSize().padding(10.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.no_matches),
                        color = Color.Black,
                        fontSize = 16.sp,

                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
                        modifier = Modifier
                            .padding(
                                top = 10.dp,
                                bottom = 10.dp
                            ),
                        textAlign = TextAlign.Center
                    )
                }

            } else {
                LazyColumn(

                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Top,

                    ) {
                    items(tournamentList) { item ->
                        TournamentsItem(item,navController,true)
                    }
                }
            }

        }
        if (isLoading) {
            CircleLoader(
                color = Color(0xFF0D1A30),
                modifier = Modifier.size(70.dp),
                isVisible = isLoading,
                secondColor = Color(0xFFED3742)
            )
        }
    }

}