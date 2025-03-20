package com.newagesmb.androidmvvmarchitecture.ui.details.scorecard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentMatchesList
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily
import kotlinx.coroutines.launch

//
// Created by Noushad on 18-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun ScoreCardTypeScreen(matches: String,navController: NavController) {
    val gson = Gson()
    val match =
        gson.fromJson(matches, TournamentMatchesList::class.java) // Deserialize JSON back to Object
    val coroutineScope = rememberCoroutineScope()
    val tabTitles = mutableListOf<String>()
    tabTitles.add(match.teamA?.team!!)
    tabTitles.add(match.teamB?.team!!)
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { tabTitles.size })
    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF3F2F2)
            ),
    ) {
        val paddingValues = WindowInsets.systemBars.asPaddingValues()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(paddingValues.calculateTopPadding())
                .background(color = Color(0xFF029634))
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color(0xFF029634))
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "back",
                modifier = Modifier
                    .clickable {
                        navController.navigateUp()
                    }

            )
            androidx.compose.material.Text(
                text = "${match.teamA?.teamCode} VS ${match.teamB?.teamCode}",
                modifier = Modifier
                    .padding(start = 20.dp)
                    .weight(1f),
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Medium,
                fontFamily = customFontFamily,
            )
        }
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(Color.Transparent)
                )
            },
            divider = {}
        ) {
            tabTitles.forEachIndexed { index, title ->
                val isSelected = index == pagerState.currentPage
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .weight(1f) // Ensures equal width for tabs
                            .background(
                                if (isSelected) Color(0xFF19A463) else Color(
                                    0xFFB3AEAE
                                )
                            )
                            .clickable {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                            .padding(vertical = 5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            )
                        )
                    }
                }

            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            when (page) {

                0 -> {

                    ScorecardScreen(
                        tournamentId = match.tournamentId,
                        matchId = match.id,
                        teamId = match.teamAId
                    )
                }

                else -> {
                    ScorecardScreen(
                        tournamentId = match.tournamentId,
                        matchId = match.id,
                        teamId = match.teamBId
                    )

                }
            }
        }

    }

}