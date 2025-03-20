package com.newagesmb.androidmvvmarchitecture.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamList
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamPlayer
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentMatchesList
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.JoinContestViewModel
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily

//
// Created by Noushad on 15-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@OptIn(ExperimentalLayoutApi::class)
@Preview(showBackground = true)
@Composable
fun MyTeamPreviewScreen(navController: NavHostController,teamList:String) {

    val gson = Gson()
    val match =
        gson.fromJson(teamList, TeamList::class.java)
    val playersListTypes = match.teamPlayers.groupBy { it.playerPositionId }

//    val playersListByTypes = joinContestViewModel.selectedPlayersList.groupBy { it.posId }
    val playersList = playersListTypes.values.toMutableList()
    val posIds = playersListTypes.keys.toMutableList()
    val paddingValues = WindowInsets.systemBars.asPaddingValues()

//    LaunchedEffect(Unit){
//        joinContestViewModel.getTeamCount()
//    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF029634))
            .padding(horizontal = 10.dp, vertical = 15.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = Color(0xFF029634))
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = 15.dp,
                    start = 15.dp,
                    end = 15.dp
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
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
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {

                itemsIndexed(playersList) { index, items ->
                    val posName = match.teamPlayers.find { it.playerPosition.id == posIds[index] }
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                        Text(
                            text = "${posName?.playerPosition?.role}",
                            modifier = Modifier
                                .wrapContentSize() // Ensures text is centered
                                .padding(horizontal = 5.dp, vertical = 2.dp)
                                .drawBehind {
                                    val strokeWidthPx = 2.dp.toPx()

                                    val textWidth =
                                        this@drawBehind.size.width // Full width of the Text Composable
                                    val startX = (size.width - textWidth) / 2
                                    val endX = startX + textWidth

                                    drawLine(
                                        color = Color.Red,
                                        strokeWidth = strokeWidthPx,
                                        start = Offset(0f, size.height + 2f), // Start at left
                                        end = Offset(size.width, size.height + 2f) // End at right
                                    )
                                },
                            fontSize = 15.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_bold)
                            ),
                            color = Color.White

                        )
                    }

                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        items.forEach {
                            MySelectedPlayer(it, posName?.playerPosition?.roleCode!!)
                        }

                    }
                }
            }
        }

    }

}

@Composable
fun MySelectedPlayer(players: TeamPlayer, positionCode: String) {
    Column(
        modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        var image = R.drawable.cricket_player
        when {
            positionCode.contains("BAT") -> {
                image = R.drawable.batter
            }
            positionCode.contains("Bowler") -> {
                image = R.drawable.bowler
            }
            positionCode.contains("WK") -> {
                image = R.drawable.cricket_player
            }
            positionCode.contains("AR") -> {
                image = R.drawable.all_rounder
            }
        }
        Image(
            painter = painterResource(id = image),
            contentDescription = "player",
            modifier = Modifier.size(50.dp)
        )
        var playerName = ""
        players.player?.let {
            playerName = it.playerName
            if (players.isCaptain == 1){
                playerName += " (C)"
            } else if (players.isViceCaptain == 1){
                playerName += " (VC)"
            }
        }

        Text(
            text = playerName,
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 5.dp, vertical = 2.dp),
            fontSize = 12.sp,
            color = Color.White,
            textAlign = TextAlign.Start,
            fontFamily = FontFamily(
                Font(R.font.roboto_medium)
            )
        )
    }
}