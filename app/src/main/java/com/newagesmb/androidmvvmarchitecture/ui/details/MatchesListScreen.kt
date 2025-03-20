package com.newagesmb.androidmvvmarchitecture.ui.details

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamList
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentMatchesList
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Locale

//
// Created by Noushad on 17-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun MatchesListScreen(tournamentId:Int, navController: NavController){

    val coroutineScope = rememberCoroutineScope()
    val viewModel: DetailsViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val tournament_id by remember { mutableStateOf(tournamentId) }
    val matchList = remember { mutableStateListOf<TournamentMatchesList>().apply { addAll(emptyList()) } }
    val listState = rememberLazyListState()
    val shouldLoadMore = remember {
        derivedStateOf {
            // Get the total number of items in the list
            val totalItemsCount = listState.layoutInfo.totalItemsCount

            // Get the index of the last visible item
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val currentPage = viewModel.currentPages?: 0
            val totalPage = viewModel.totalPages ?: 0
            Log.d("visibleItemsCount", "$lastVisibleItemIndex")
            // Check if we have scrolled near the end of the list and more items should be loaded
            lastVisibleItemIndex >= (totalItemsCount - 2) && !isLoading && totalItemsCount != 0 && currentPage < totalPage
        }
    }

// Launch a coroutine to load more items when shouldLoadMore becomes true
    LaunchedEffect(listState) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }  // Ensure that we load more items only when needed
            .collect {
                Log.d("PlayerListBottomReached", "true")
                val currentPage = viewModel.currentPages ?: 0
                viewModel.getMatches(tournament_id, currentPage + 1)
//                loadMoreItems()
            }
    }

    LaunchedEffect(tournament_id) {
        launch {
            viewModel.getMatches(tournament_id,1)
            viewModel.matchesLists.collectLatest {
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
                        // Handle success state
                        if (!it.data?.data?.tournamentMatches.isNullOrEmpty()){
                            val updatedMatches = it.data?.data?.tournamentMatches?.map { match ->
                                match.copy(isEditEnabled = viewModel.checkEditAvailability(match.matchDate!!, match.matchTime!!))
                            }
                            matchList.addAll(updatedMatches!!)
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
        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .wrapContentHeight()
//            .paint(
//                painter = painterResource(id = R.drawable.my_team_bg),
//                contentScale = ContentScale.FillBounds
//            )
//        .background(color = Color.White)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFE6E6E6)
                ).padding(top = 10.dp),
            verticalArrangement = Arrangement.Top,

            ) {
            itemsIndexed(matchList){index,it->

                Row(
                    modifier = Modifier
                        .padding(horizontal = 5.dp, vertical = 5.dp)
                        .background(color = Color.White).clickable {
                            if (it.matchPlayesScoreCount > 0) {
                                val gson = Gson()
                                val jsonString = gson.toJson(it)
                                navController.navigate("${AppNavigation.SCORE_CARD.name}/$jsonString")
                            }
                        }
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "M${index+1} - ${it.teamA?.teamCode} VS ${it.teamB?.teamCode}",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .weight(1f),
                        color = Color.Black,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontFamily = customFontFamily,
                    )
                    if (it.isEditEnabled){
                        Image(
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "back",
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .clickable {
//                            navController.navigateUp()
                                }

                        )
                    } else if (!viewModel.checkMyTeamEditAvailability(it.matchDate!!,it.matchTime!!) && it.matchPlayesScoreCount==0){
                        val status = if (it.isAbandoned == 1) "Abandoned" else "Live"
                        Text(
                            text = status,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(end = 10.dp),
                            fontSize = 15.sp,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_medium)
                            )
                        )
                    } else {
                        if (it.matchPlayesScoreCount==0){
                            Text(
                                text = stringResource(id = R.string.upcoming),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(end = 10.dp),
                                fontSize = 15.sp,
                                color = Color(0xFF515151),
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(
                                    Font(R.font.roboto_regular)
                                )
                            )
                        }

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
