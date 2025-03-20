package com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamList
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily
import kotlinx.coroutines.launch

//
// Created by Noushad on 06-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun CreateTeamScreen(tournamentId : Int =0,tournamentType : Int =0,tournamentPrice:String = "",teamList:String = "",navController: NavHostController, joinContestViewModel: JoinContestViewModel) {
    val tabs = listOf("WK", "BAT", "AR", "BOWL")
    var selectedTabIndex by remember { mutableStateOf(0) }

    val coroutineScope = rememberCoroutineScope()
    var selectedCount by remember { mutableStateOf(0) }
    var showPopup by remember { mutableStateOf(false) }
    if (teamList.isNotEmpty() && teamList != "empty"){
        val gson = Gson()
        val teamsList =
            gson.fromJson(teamList, TeamList::class.java)
        joinContestViewModel.teamList = teamsList.teamPlayers.toMutableList()
        joinContestViewModel.tournamentTeamId = teamsList.id

    }
//    val joinContestViewModel : JoinContestViewModel = hiltViewModel()
    val pagerState = rememberPagerState(pageCount = { joinContestViewModel.playerTypesList.size })
    LaunchedEffect(tournamentId) {

        launch {

            joinContestViewModel.tournamentId = tournamentId
            joinContestViewModel.tournamentType = tournamentType

            joinContestViewModel.getPlayerTypes(tournamentId)
            joinContestViewModel.getTeamCreationCriteria(tournamentId)


        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color(0xFF029634))
                    .padding(15.dp)
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
                    Text(
                        text = stringResource(id = R.string.create_team),
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {

                    Column(
                        modifier = Modifier
                            .padding(start = 5.dp, top = 10.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = stringResource(id = R.string.players),
                            color = Color.White,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            fontFamily = customFontFamily,
                        )
                        Text(
                            text = "${joinContestViewModel.selectedPlayersList.size}/${joinContestViewModel.maxPlayerLimit}",
                            modifier = Modifier
                                .padding(top = 3.dp),
                            color = Color.White,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Normal,
                            fontFamily = customFontFamily,
                        )
                    }

//                    Column(
//                        modifier = Modifier
//                            .padding(top = 10.dp),
//                        horizontalAlignment = Alignment.End
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.credit_left),
//                            color = Color.White,
//                            fontSize = 13.sp,
//                            textAlign = TextAlign.Start,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = customFontFamily,
//                        )
//                        Text(
//                            text = "100",
//                            modifier = Modifier
//                                .padding(top = 3.dp),
//                            color = Color.White,
//                            fontSize = 14.sp,
//                            textAlign = TextAlign.Start,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = customFontFamily,
//                        )
//                    }

                }


            }
            if (joinContestViewModel.playerTypesList.isNotEmpty()){

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ScrollableTabRow(
                        selectedTabIndex = pagerState.currentPage,
                        modifier = Modifier.wrapContentWidth(),
                        edgePadding = 0.dp,
                        divider = {},
                        indicator = { tabPositions ->
                            TabRowDefaults.Indicator(
                                Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                color = Color.Transparent // No underline indicator
                            )
                        },
                        containerColor = Color.Transparent
                    ) {
                        joinContestViewModel.playerTypesList.forEachIndexed { index, item ->
                            val isSelected = index == pagerState.currentPage
                            Tab(
                                selected = isSelected,
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(index)
                                    }
                                },
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier
                                        .padding(4.dp)
                                ) {
                                    Text(
                                        text = "${item.roleCode}",
                                        color = if (isSelected) Color.Red else Color.Gray,
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Start,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = FontFamily(
                                            Font(R.font.roboto_medium)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    beyondBoundsPageCount = if (joinContestViewModel.teamList.isNotEmpty()) joinContestViewModel.playerTypesList.size - 1 else 0 // Load all pages if updating
                ) { page ->
                    PlayerListScreen(joinContestViewModel.playerTypesList[page].id, joinContestViewModel,tournamentId){

                    }
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {

                Button(

                    onClick = {
                        Log.d("Infos",joinContestViewModel.getInfos().toString())
                        if (joinContestViewModel.selectedPlayersList.isNotEmpty())
                            navController.navigate(AppNavigation.SELECTED_PLAYERS_PREVIEW.name)

//                          isLoading = true
                    },
                    modifier = Modifier
                        .weight(.5f),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    )
                    //, shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.preview),
                        color = Color.Black,
                        fontSize = 16.sp,

                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
                        modifier = Modifier.padding(
                            top = 10.dp,
                            bottom = 10.dp
                        )
                    )

                }
                Button(

                    onClick = {

                        if (joinContestViewModel.isCriteriaSatisfied){
                            if (joinContestViewModel.teamList.isEmpty())
                                showPopup = true
                            else
                                navController.navigate(AppNavigation.TEAM_PREVIEW.name)
                        }


//                          isLoading = true
                    },
                    modifier = Modifier
                        .weight(.5f),
                    colors = ButtonDefaults.buttonColors(

                        backgroundColor = if (joinContestViewModel.isCriteriaSatisfied) Color(
                            0xFF029634
                        ) else Color(0xFF4F8A63)
                    )
                    //, shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.next),
                        color = if (joinContestViewModel.isCriteriaSatisfied)  Color.White else Color(
                            0xFFC2BABA
                        ),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_bold)
                        ),
//                fontFamily = customFontFamily,
                        modifier = Modifier.padding(

                            top = 10.dp,
                            bottom = 10.dp
                        )
                    )

                }

            }
        }


        PaymentPopupScreen(showPopup = showPopup,tournamentPrice) {
            showPopup = false
            navController.navigate(AppNavigation.TEAM_PREVIEW.name)
        }
    }
}