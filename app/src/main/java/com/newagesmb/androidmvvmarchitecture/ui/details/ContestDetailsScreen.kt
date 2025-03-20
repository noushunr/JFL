package com.newagesmb.androidmvvmarchitecture.ui.details

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamList
import com.newagesmb.androidmvvmarchitecture.data.model.response.Tournaments
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.JoinContestViewModel
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.CommonUtils
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//
// Created by Noushad on 25-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun ContestDetailsScreen(
    tournamentId: Int,
    navController: NavHostController,
    sharedViewModel: JoinContestViewModel
) {
    var tabs by remember { mutableStateOf(listOf("MyContest", "My Teams", "Matches", "Stats")) }
    var contestCount by remember { mutableStateOf(2) }
    var teamsCount by remember { mutableStateOf(2) }
    var tournamentName by remember { mutableStateOf("") }
    var tournamentType by remember { mutableStateOf("") }
    var tournament by remember { mutableStateOf(Tournaments()) }
    val myContestList = remember { mutableStateListOf<TeamList>().apply { addAll(emptyList()) } }
//    val tournamentList = remember { mutableStateListOf<Tournaments>().apply { addAll(emptyList()) } }
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val coroutineScope = rememberCoroutineScope()
    val viewModel: DetailsViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    LaunchedEffect(tournamentId) {
        launch {
            viewModel.getMyMatches(tournamentId)
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
                        // Handle success state
                        if (!it?.data?.teamList.isNullOrEmpty()) {

                            contestCount = it.data?.teamList?.size!!
                            teamsCount = it.data?.teamList?.size!!
                            tournamentName = it.data.tournament?.tournamentName!!
                            tournament = it.data.tournament
                            myContestList.clear()
                            it.data.teamList.forEach {
                                val teams = it
                                teams.isEditEnabled = viewModel.checkMyTeamEditAvailability(tournament.startDate!!,tournament.startTime!!)
                                myContestList.add(teams)
                            }
//                            myContestList.addAll(it.data?.teamList)

//                            val updatedList = myContestList.map {
//                                it.copy(isEditEnabled = viewModel.checkMyTeamEditAvailability(tournament.startDate!!,tournament.startTime!!))
//                            }
                            tabs = listOf(
                                "MyContest($contestCount)",
                                "My Teams($teamsCount)",
                                "Matches",
                                "Stats"
                            )
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
                        text = tournamentName,
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
            }

            ScrollableTabRow(
                selectedTabIndex = pagerState.currentPage,
                edgePadding = 0.dp,
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp)
                    .fillMaxWidth(),
                divider = {},
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        color = Color.Transparent
                    )
                },
                containerColor = Color.Transparent
            ) {
                tabs.forEachIndexed { index, title ->
                    val isSelected = index == pagerState.currentPage
                    Tab(
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 8.dp) // Reduce gap between tabs
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Color.Black else Color(0xA11C1C1C),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }
            }

//            ScrollableTabRow(
//                selectedTabIndex = pagerState.currentPage,
//                edgePadding = 0.dp,
//                modifier = Modifier.background(color = Color.White).padding(start=10.dp, end = 10.dp).fillMaxWidth(),
//                divider = {},
//                indicator = { tabPositions ->
//                    TabRowDefaults.Indicator(
//                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
//                        color = Color.Transparent // No underline indicator
//                    )
//                },
//                containerColor = Color.Transparent
//            ) {
//
//                tabs.forEachIndexed { index, title ->
//                    val isSelected = index == pagerState.currentPage
//                    Tab(
//                        selected = isSelected,
//                        onClick = {
//                            coroutineScope.launch {
//                                pagerState.animateScrollToPage(index)
//                            }
//                        },
//                        modifier = Modifier.padding(horizontal = 3.dp, vertical = 4.dp)
//                    ) {
//                        Text(
//                            text = title,
//                            color = if (isSelected) Color.Black else Color(0xA11C1C1C),
//                            fontSize = 16.sp,
//                            textAlign = TextAlign.Center,
//                            fontWeight = FontWeight.Normal,
//                            fontFamily = FontFamily(
//                                Font(R.font.roboto_regular)
//                            )
//                        )
//                    }
//                }
//            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                beyondBoundsPageCount = 0
            ) { page ->
                when (page) {
                    0 -> LazyColumn(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFE6E6E6)
                            ),
                        verticalArrangement = Arrangement.Top,

                        ) {
                        items(myContestList) {
                            MyContestItem(navController, true, it, tournament, viewModel.userName)
                        }
                    }

                    1 -> LazyColumn(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFE6E6E6)
                            ),
                        verticalArrangement = Arrangement.Top,

                        ) {
                        itemsIndexed(myContestList) { index, item ->

                            MyTeamsScreen(item, index + 1, onClick = {
                                val gson = Gson()
                                val jsonString = gson.toJson(it)
                                navController.navigate("${AppNavigation.MY_TEAM_PREVIEW.name}/$jsonString")
                            }, onEdit = {
                                sharedViewModel.clearData()
                                val gson = Gson()
                                val jsonString = gson.toJson(it)
                                val type = if (tournament?.tournamentType?.equals("domestic")!!) 0 else 1
                                var price = tournament.price?.toDouble()?.toInt().toString()?:""
                                navController.navigate("${AppNavigation.CREATE_TEAM.name}/$tournamentId/$type/$price/$jsonString")
                            })
                        }
                    }

                    2 -> Box(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFE6E6E6)
                            )

                    ) {
                        MatchesListScreen(tournamentId,navController)
                    }

                    3 -> StatusScreen(tournamentId)
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

@Composable
fun MyContestItem(
    navController: NavHostController,
    isUpcoming: Boolean = false,
    teamList: TeamList,
    tournaments: Tournaments,
    userName: String
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 10.dp)
            .clickable {
//                navController.navigate(AppNavigation.PRICE_POOL.name)
            }
        //.background(color = Color.White)
    ) {
        Column() {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = userName,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 5.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                Text(
                    text = CommonUtils.formatDateNew(teamList.createdAt ?: "2025-03-21", "d MMM"),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 5.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )

            }
            Divider(
                color = Color(0xFFE6E4E4),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(start = 5.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(start = 5.dp)
                    ) {
                        Text(
                            text = "Price",
                            modifier = Modifier
                                .wrapContentWidth(),
                            fontSize = 14.sp,
                            color = Color(0xFF4D4D4D),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            )
                        )
                        Text(
                            text = "₹${tournaments.totalPrizeMoney}",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_medium)
                            ),
//                fontFamily = customFontFamily,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 10.dp)
                        )
                    }
                }

                if (CommonUtils.isPastTime(tournaments.startDate!!,tournaments.startTime!!)){
                    Text(
                        text = "Upcoming",
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontSize = 14.sp,
                        color = Color(0xFF4D4D4D),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        )
                    )
                } else if(teamList.tournamentPoint.isNullOrEmpty()){
                    Text(
                        text = "Live",
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontSize = 14.sp,
                        color = Color(0xFF4D4D4D),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        )
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Points",
                            modifier = Modifier
                                .wrapContentWidth(),
                            fontSize = 16.sp,
                            color = Color(0xFF4D4D4D),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_medium)
                            )
                        )
                        Text(
                            text = teamList.tournamentPoint ?: "",
                            modifier = Modifier
                                .wrapContentWidth(),
                            fontSize = 20.sp,
                            color = Color(0xFF952A64),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            )
                        )
                    }
                }
//                if (isUpcoming) {
//                    Text(
//                        text = "Upcoming",
//                        modifier = Modifier
//                            .wrapContentWidth(),
//                        fontSize = 14.sp,
//                        color = Color(0xFF4D4D4D),
//                        textAlign = TextAlign.Center,
//                        fontFamily = FontFamily(
//                            Font(R.font.roboto_regular)
//                        )
//                    )
//                } else {
//                    Column(
//                        horizontalAlignment = Alignment.CenterHorizontally,
//                        verticalArrangement = Arrangement.Center
//                    ) {
//                        Text(
//                            text = "Points",
//                            modifier = Modifier
//                                .wrapContentWidth(),
//                            fontSize = 16.sp,
//                            color = Color(0xFF4D4D4D),
//                            textAlign = TextAlign.Center,
//                            fontFamily = FontFamily(
//                                Font(R.font.roboto_medium)
//                            )
//                        )
//                        Text(
//                            text = teamList.tournamentPoint ?: "",
//                            modifier = Modifier
//                                .wrapContentWidth(),
//                            fontSize = 20.sp,
//                            color = Color(0xFF952A64),
//                            textAlign = TextAlign.Center,
//                            fontFamily = FontFamily(
//                                Font(R.font.roboto_regular)
//                            )
//                        )
//                    }
//                }

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    Text(
                        text = "Entry",
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontSize = 14.sp,
                        color = Color(0xFF4D4D4D),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        )
                    )
                    Text(
                        text = "₹${tournaments.price}",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_medium)
                        ),
//                fontFamily = customFontFamily,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(top = 10.dp)
                    )
                }
            }

            Divider(
                color = Color(0xFFE6E4E4),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${tournaments.tournamentPlayersCount} Players",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 5.dp),
                    fontSize = 14.sp,
                    color = Color(0xFF8D8D8D),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                Text(
                    text = "${tournaments.userTournamentTeamsCount} Contest",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 5.dp),
                    fontSize = 14.sp,
                    color = Color(0xFF8D8D8D),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
//                if (isUpcoming){
//                    Text(
//                        text = "${tournaments.tournamentPlayersCount} Players",
//                        modifier = Modifier
//                            .wrapContentWidth()
//                            .padding(start = 5.dp),
//                        fontSize = 14.sp,
//                        color = Color(0xFF8D8D8D),
//                        textAlign = TextAlign.Center,
//                        fontFamily = FontFamily(
//                            Font(R.font.roboto_regular)
//                        )
//                    )
//                    Text(
//                        text = "${tournaments.userTournamentTeamsCount} Contest",
//                        modifier = Modifier
//                            .wrapContentWidth()
//                            .padding(end = 5.dp),
//                        fontSize = 14.sp,
//                        color = Color(0xFF8D8D8D),
//                        textAlign = TextAlign.Center,
//                        fontFamily = FontFamily(
//                            Font(R.font.roboto_regular)
//                        )
//                    )
//                } else {
//                    Text(
//                        text = "Spots 1200",
//                        modifier = Modifier
//                            .wrapContentWidth()
//                            .padding(start = 5.dp),
//                        fontSize = 14.sp,
//                        color = Color.Black,
//                        textAlign = TextAlign.Center,
//                        fontFamily = FontFamily(
//                            Font(R.font.roboto_regular)
//                        )
//                    )
//                    Text(
//                        text = "#1, 077",
//                        modifier = Modifier
//                            .wrapContentWidth()
//                            .padding(end = 5.dp),
//                        fontSize = 14.sp,
//                        color = Color.Black,
//                        textAlign = TextAlign.Center,
//                        fontFamily = FontFamily(
//                            Font(R.font.roboto_regular)
//                        )
//                    )
//                }


            }
        }
    }
}