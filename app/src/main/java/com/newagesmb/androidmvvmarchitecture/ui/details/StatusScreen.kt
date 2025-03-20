package com.newagesmb.androidmvvmarchitecture.ui.details

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentMatchesList
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentStats
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.util.Locale

//
// Created by Noushad on 02-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview(showBackground = true)
fun StatusScreen(tournamentId:Int){

    val coroutineScope = rememberCoroutineScope()
    val viewModel: DetailsViewModel = hiltViewModel()
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val statsList = remember { mutableStateListOf<TournamentStats>().apply { addAll(emptyList()) } }
    val listState = rememberLazyListState()
    var expanded by remember { mutableStateOf(false) }
    var matchId by remember { mutableStateOf(-1) }
    val tournament_id by remember { mutableStateOf(tournamentId) }
    val matchList = remember { mutableStateListOf<TournamentMatchesList>().apply { add(TournamentMatchesList(id = -1)) } }
    var currentPageMatch by remember { mutableStateOf(0) }
    var paginatedList by remember { mutableStateOf(matchList) }
    var selectedItem by remember { mutableStateOf("Overall") }
    val options = mutableListOf<String>("Overall")
    val scrollState = rememberScrollState()
    var isAtBottom by remember { mutableStateOf(false) }
    LaunchedEffect(scrollState.value) {
        isAtBottom = scrollState.value >= (scrollState.maxValue - 10) // Buffer of 10px
        if (isAtBottom){
            if (viewModel.currentPageMatch<viewModel.totalPageMatch){
                viewModel.currentPageMatch = viewModel.currentPageMatch +1
                viewModel.getMatchesForStats(tournamentId,viewModel.currentPageMatch)
            }
        }
    }

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
                if (matchId == -1)
                    viewModel.getStats( tournamentId, currentPage + 1)
                else
                    viewModel.getMatchStats( matchId, currentPage + 1)
//                loadMoreItems()
            }
    }

    LaunchedEffect(key1 = tournament_id){
        launch {
            viewModel.getMatchesForStats(tournament_id,1)
            viewModel.matchesStatsLists.collectLatest {
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

//                            matchList.add(TournamentMatchesList(id = -1))
                            matchList.addAll(it.data?.data?.tournamentMatches!!)
//                            matchList.forEach {
//                                options.add("${it.teamA?.teamCode} VS ${it.teamB?.teamCode}")
//                            }
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
    LaunchedEffect(matchId) {
        launch {
            if (matchId==-1) {
                statsList.clear()
                viewModel.getStats(tournamentId, 1)
            }
            else{
                statsList.clear()
                viewModel.getMatchStats(matchId, 1)
            }

            viewModel.statsListState.collectLatest {
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
                        if (!it.data?.data?.tournamentStats.isNullOrEmpty()){
                            statsList.addAll(it.data?.data?.tournamentStats!!)
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

    Box(){
        Column {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.background(Color.White)
                ) {
                    TextField(
                        value = selectedItem,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth().background(Color.White),
                        trailingIcon = {
                            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = 350.dp) // Fixes measurement issue
                                .verticalScroll(scrollState) // Manually handle scrolling
                        ) {
                            matchList.forEachIndexed { index, option ->
                                DropdownMenuItem(
                                    modifier = Modifier.background(Color.Transparent), // Remove item background
                                    interactionSource = remember { MutableInteractionSource() }, // Remove ripple
                                    leadingIcon = null, // Ensure no default icon,
                                    text = {
                                        val matchName = if(option.teamA!=null ) "M${index} - ${option.teamA?.teamCode} VS ${option.teamB?.teamCode}" else "Overall"
                                        Text(text = matchName,
                                            fontSize = 15.sp,
                                            color = Color(0xFF3B3B3B),
                                            textAlign = TextAlign.Start,
                                            fontFamily = FontFamily(
                                                Font(R.font.roboto_regular)
                                            ))

                                    },
                                    onClick = { selectedItem = if(option.teamA!=null ) "${option.teamA?.teamCode} VS ${option.teamB?.teamCode}" else "Overall"
                                        matchId = option.id
                                        expanded = false })
                            }
                        }

                    }
                }
            }
            Row(
                modifier = Modifier
                    .background(color = Color(0xFFF5F5F5))
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.contestents).toUpperCase(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp).weight(.8f),
                    fontSize = 12.sp,
                    color = Color(0xFF3B3B3B),
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )

                Text(
                    text = stringResource(id = R.string.points).toUpperCase(Locale.getDefault()),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 10.dp).weight(0.2f),
                    fontSize = 12.sp,
                    color = Color(0xFF3B3B3B),
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )


            }

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.Top,

                ) {
                items(statsList) {

                    WinnersList(it)
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
@Preview(showBackground = true)
fun WinnersList(tournamentStats: TournamentStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(0.8f)) {
            Image(
                painter = painterResource(R.drawable.ic_tournament),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)                       // clip to the circle shape // add a border (optional)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${tournamentStats.contestantName}",
                    modifier = Modifier
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
//                Text(
//                    text = "KKR",
//                    modifier = Modifier
//                        .wrapContentWidth(),
//                    fontSize = 11.sp,
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(
//                        Font(R.font.roboto_light)
//                    )
//                )
            }

        }
        Text(
            text = "${tournamentStats.point}",
            modifier = Modifier
                .wrapContentWidth()
                .padding(end = 10.dp).weight(0.2f),
            fontSize = 12.sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            fontFamily = FontFamily(
                Font(R.font.roboto_regular)
            )
        )
    }
}