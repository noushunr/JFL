package com.newagesmb.androidmvvmarchitecture.ui.home

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Text
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.PlayerListScreen
import kotlinx.coroutines.launch

//
// Created by Noushad on 20-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun MyMatchesScreen(navController: NavHostController) {
    val tabs = remember{ listOf("Upcoming", "Live", "Completed")}
    val pagerState = rememberPagerState(pageCount = { tabs.size })
//   val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    var selectedCount by remember { mutableStateOf(0) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val viewModel: HomeViewModel = hiltViewModel()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    Column() {
                        DrawerHeader(viewModel)
                        DrawerContent { item ->
                            if (item == "Logout") {
                                viewModel.clearSession {
                                    navController.navigate(AppNavigation.LOGIN.name) {
                                        popUpTo(AppNavigation.MY_MATCHES.name) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            // Handle item clicks here
                        }
                    }

                }

            }

        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(drawerState)
//                TopAppBar(
//                    title = { Text(text = "My App") },
//                    navigationIcon = {
//                        IconButton(onClick = { drawerState.open() }) {
//                            Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
//                        }
//                    }
//                )
            },
            content = { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF029634))
                        .padding(innerPadding)
                ) {
                    Column(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFFF3F2F2)
                            ),
                    ) {

                        ScrollableTabRow(
                            selectedTabIndex = pagerState.currentPage,

                            modifier = Modifier
                                .background(color = Color.White)
                                .padding(10.dp)
                                .fillMaxWidth(),
                            divider = {},
                            indicator = { tabPositions ->
                                TabRowDefaults.Indicator(
                                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                                    color = Color.Transparent // No underline indicator
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
                                    modifier = Modifier.padding(vertical = 4.dp)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .padding(4.dp)
                                    ) {
                                        Text(
                                            text = title,
                                            color = if (isSelected) Color.Black else Color.Gray,
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
                        HorizontalPager(
                            state = pagerState,
                            beyondBoundsPageCount = 0,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) { page ->
                            Log.d("CurrwntIndex", "${pagerState.currentPage}")
                            if (page == pagerState.currentPage) { // Only load when it's the current page
                                MyMatchesByTypeScreen(
                                    navController = navController,
                                    type = tabs[page]
                                )
                            }
//                            MyMatchesByTypeScreen(navController = navController, type = tabs[pagerState.currentPage])
//
                        }
                    }
                }
            })

    }


}