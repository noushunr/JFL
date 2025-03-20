package com.newagesmb.androidmvvmarchitecture.ui.details.scorecard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.MatchPlayers
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.model.response.ScorecardResponse
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentStats
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.ui.details.DetailsViewModel
import com.newagesmb.androidmvvmarchitecture.ui.details.WinnersList
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

//
// Created by Noushad on 17-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun ScorecardScreen(tournamentId:Int, matchId:Int, teamId:Int){
    val viewModel: DetailsViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(true) }
//    val batStats = remember { mutableStateListOf<MatchPlayers>().apply { addAll(emptyList()) } }
    val context = LocalContext.current
    val batStats = remember { mutableStateListOf<MatchPlayers>() }

    // Get state from ViewModel
    val scoreCardState by viewModel.scoreCardState.collectAsState()

    // Fetch data only if not already cached
    LaunchedEffect(teamId) {
        if (!scoreCardState.containsKey(teamId)) {
            viewModel.getScoreCard(tournamentId, matchId, teamId)
        } else {
            updateBatStats(scoreCardState[teamId], batStats)
        }
    }

    // Observe changes and update UI
    LaunchedEffect(scoreCardState[teamId]) {
        scoreCardState[teamId]?.let { updateBatStats(it, batStats) }
    }
//    LaunchedEffect(teamId) {
//        launch {
//            viewModel.getScoreCard(tournamentId,matchId,teamId)
//            viewModel.scoreCardState.collectLatest {
//                when (it) {
//                    is Resource.Loading -> {
//                        isLoading = true
//                        // Show loading state
//                    }
//
//                    is Resource.StartLoading -> {
//
//                        isLoading = true
//                    }
//
//                    is Resource.Success -> {
//
//                        isLoading = false
//                        // Handle success state
//                        if (!it.data?.matchPlayersA.isNullOrEmpty()){
//                            batStats.clear()
//                            batStats.addAll(it.data?.matchPlayersA!!)
//
//                        }
////                        if (!it.data?.matchPlayersB.isNullOrEmpty()){
////                            bowlStats.addAll(it.data?.matchPlayersA!!)
////                        }
//
//                    }
//
//                    is Resource.Error -> {
//                        isLoading = false
//                        context.showToast(it?.errorMessage)
//                    }
//                }
//            }
//        }
//    }
    Box( modifier = Modifier
        .background(color = Color(0xFFF5F5F5))
        .padding(horizontal = 10.dp, vertical = 10.dp)
        .fillMaxWidth()){

        LeagueTableScreen(playerStats = batStats)

//        Column {
//
//
//            LazyColumn( modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 10.dp, vertical = 5.dp),
//                verticalArrangement = Arrangement.Top,){
//                item {
//                    BattingHeader()
//                }
//                items(batStats) {
//                    BattingStats(it)
//                }
//                item {
//                    Column {
//                        Divider(
//                            color = Color(0x90363535),
//                            thickness = 1.dp,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 3.dp, bottom = 10.dp)
//                        )
//                        BowlingHeader()
//                    }
//
//                }
//                items(bowlStats){
//                    BowlingStats(score = it)
//                }
//
//            }
//
//        }
    }

}
private fun updateBatStats(
    resource: Resource<ScorecardResponse>?,
    batStats: SnapshotStateList<MatchPlayers>
) {
    if (resource is Resource.Success) {
        batStats.clear()
        resource.data?.matchPlayersA?.let { batStats.addAll(it) }
    }
}