package com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.model.response.PlayersList
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

//
// Created by Noushad on 15-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun PlayerListScreen(
    playerTypeId: Int,
    joinContestViewModel: JoinContestViewModel,
    tournamentId: Int = 0,
    function: (selectedCount: Int) -> Unit
) {
    var selectedCount by remember { mutableStateOf(0) }
    val listState = rememberLazyListState()
    // Coroutine scope for handling background operations like loading data
    val coroutineScope = rememberCoroutineScope()
    // State to track if more items are being loaded
    var isLoading by remember { mutableStateOf(false) }
    var type by rememberSaveable {
        mutableStateOf(playerTypeId)
    }
//    val playerList = remember { mutableStateListOf<PlayersList>().apply { addAll(emptyList()) } }
    val playerListState = joinContestViewModel.playerList.collectAsState().value
    val cachedPlayers = joinContestViewModel.playerDataCache[playerTypeId] ?: mutableListOf()
    val playerHeader = joinContestViewModel.teamTypeLabel[playerTypeId]

    LaunchedEffect(playerTypeId) {
        type = playerTypeId
        if (cachedPlayers.isEmpty()) {

            joinContestViewModel.getPlayersList(playerTypeId, tournamentId)
        }
    }

    val shouldLoadMore = remember {
        derivedStateOf {
            // Get the total number of items in the list
            val totalItemsCount = listState.layoutInfo.totalItemsCount
            Log.d("totalItemsCount", "$totalItemsCount")
            Log.d("PlayerTypeId ", "$type")
            // Get the index of the last visible item
            val lastVisibleItemIndex =
                listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val currentPage = joinContestViewModel.currentPagesByType[type]?.toInt() ?: 0
            val totalPage = joinContestViewModel.totalPagesByType[type] ?: 0
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
                val currentPage = joinContestViewModel.currentPagesByType[type]?.toInt() ?: 0
                joinContestViewModel.paginatePlayerList(type, tournamentId, currentPage + 1)
//                loadMoreItems()
            }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = Color(0xFFF3F2F2)
            )
    ) {
        Column(

            modifier = Modifier
                .fillMaxSize()
//                .padding(horizontal = 10.dp),

        ) {
            Text(
                text = playerHeader ?: "",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(10.dp),
                fontSize = 14.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(
                    Font(R.font.roboto_bold)
                )
            )
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.players),
                    modifier = Modifier
                        .weight(.5f),
                    fontSize = 12.sp,
                    color = Color(0xFF3B3B3B),
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )

//                Text(
//                    text = stringResource(id = R.string.points),
//                    modifier = Modifier
//                        .weight(.25f),
//                    fontSize = 12.sp,
//                    color = Color(0xFF3B3B3B),
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(
//                        Font(R.font.roboto_regular)
//                    )
//                )
//                Text(
//                    text = stringResource(id = R.string.credits),
//                    modifier = Modifier
//                        .weight(.25f),
//                    fontSize = 12.sp,
//                    color = Color(0xFF3B3B3B),
//
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(
//                        Font(R.font.roboto_regular)
//                    )
//                )

            }

            Divider(
                color = Color.White,
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
            val teamSelectionCounts =
                joinContestViewModel.playerDataCache.mapValues { (_, players) ->
                    players.count { it.player?.isSelected!! }
                }
            val teamSelectionCount = cachedPlayers
                .filter { it.player?.isSelected!! }
                .groupingBy { it.teamId }
                .eachCount()
            LazyColumn(

                state = listState,
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,

                ) {
                itemsIndexed(cachedPlayers) { index, item ->
                    val selectedPlayerCountByType =
                        joinContestViewModel.selectedPlayersList.count { it.isSelected && it.posId == playerTypeId }


                    val limit = joinContestViewModel.maxReachedTeamIds.count { it == item.teamId }

                    val foreignPlayerCount =
                        joinContestViewModel.selectedPlayersList.count { it.isSelected && it.isForeigner == 1 }
                    val nonForeignPlayerCount =
                        joinContestViewModel.selectedPlayersList.count { it.isSelected && it.isForeigner == 0 }
                    val isHosted = item.player?.isForeigner == 0

                    val isMaxReached =
                        if (isHosted){
                            if (joinContestViewModel.tournamentType == 1){
                                limit >= (joinContestViewModel.maxPlayerLimit - joinContestViewModel.foriegnPlayerLimit)
                            } else{
                                limit >= joinContestViewModel.maxPlayerLimitByTeam || nonForeignPlayerCount >= (joinContestViewModel.maxPlayerLimit - joinContestViewModel.foriegnPlayerLimit)
                            }

                        } else{
                            limit >= joinContestViewModel.maxPlayerLimitByTeam

                        }
                    val isMaxPosReached =
                        selectedPlayerCountByType >= joinContestViewModel.playerTypeLabel(
                            playerTypeId
                        )
                    val isForeignCountReached =
                        foreignPlayerCount >= joinContestViewModel.foriegnPlayerLimit
                    PlayersItem(
                        item,
                        index,
                        isMaxReached,
                        isMaxPosReached,
                        isForeignCountReached
                    ) { pos ->
                        val updatedPlayers =
                            joinContestViewModel.playerDataCache[playerTypeId]?.toMutableList()
                        if (updatedPlayers != null && pos in updatedPlayers.indices) {
                            // Create a new player object with updated playerName
                            val updatedPlayer = updatedPlayers[pos].copy(
                                player = updatedPlayers[pos].player?.copy(
                                    isSelected = !updatedPlayers[pos].player?.isSelected!!,
                                    posId = playerTypeId,
                                    teamCode = updatedPlayers[pos].team?.teamCode ?: ""
                                )
                            )


                            joinContestViewModel.addOrRemovePlayer(
                                updatedPlayer.player!!,
                                updatedPlayer.teamId
                            )
                            // Update the list with the modified player
                            updatedPlayers[pos] = updatedPlayer

                            // Update the ViewModel state
                            joinContestViewModel.updatePlayerList(playerTypeId, updatedPlayers)
                            if (joinContestViewModel.teamList.isNotEmpty())
                                joinContestViewModel.setCaptains()
                        }
//                        joinContestViewModel.playerDataCache[playerTypeId]?.get(pos)?.player?.isSelected = !joinContestViewModel.playerDataCache[playerTypeId]?.get(pos)?.player?.isSelected!!
//                        selectedCount =
//                            if (joinContestViewModel.playerDataCache[playerTypeId]?.get(pos)?.player?.isSelected!!) {
//                                selectedCount + 1
//                            } else {
//                                selectedCount - 1
//                            }
//                        function(selectedCount)
                    }
//                    if (index == cachedPlayers.lastIndex){
//
                    val currentPage =
                        joinContestViewModel.currentPagesByType[playerTypeId]?.toInt() ?: 0
                    val totalPage = joinContestViewModel.totalPagesByType[playerTypeId] ?: 0
//                        if (currentPage<totalPage){
//                            Log.d("PlayerListBottomReached","true")
//                        }
//                    }
                }
            }

        }
    }
}

@Composable
fun PlayersItem(
    playersList: PlayersList,
    index: Int,
    isMaxReached: Boolean,
    isMaxPosTypeReached: Boolean = false,
    isForeignCountReached: Boolean = false,
    function: (index: Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()

            .background(
                color =
                if (playersList.player?.isSelected!!) {
                    Color(0xFFE3CBA8)
                } else {
                    if (isMaxReached || isMaxPosTypeReached || (playersList.player.isForeigner == 1 && isForeignCountReached)) Color(
                        0xFFC5C9C9
                    ).copy(alpha = 0.7f)
                    else
                        Color(0xFFF3F2F2)
                }

//                    if (playersList.player?.isSelected!!){
//                        if (isMaxReached) Color(0xFFF2F3F3).copy(alpha = 0.7f)
//                        else
//                            Color(0xFFDBEAFB)
//                    }else
//                        Color(0xFFF3F2F2)
//                }
            )
            .padding(vertical = 10.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier.weight(.5f), verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.ic_tournament),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,            // crop the image if it's not a square
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)                       // clip to the circle shape
                    .border(2.dp, Color.White, CircleShape)   // add a border (optional)
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "${playersList.player?.playerName} (${playersList.team?.teamCode})",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 10.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_medium)
                    )
                )
                Text(
                    text = buildAnnotatedString {
                        // First part with Red color
                        append("26 ")
                        addStyle(
                            style = SpanStyle(
                                color = Color.Black, fontFamily = FontFamily(
                                    Font(R.font.roboto_medium)
                                )
                            ),
                            start = 0,
                            end = 3
                        )

                        // Second part with Blue color
                        append("Points")
                        addStyle(
                            style = SpanStyle(
                                color = Color(0xFFA09191), fontFamily = FontFamily(
                                    Font(R.font.roboto_regular)
                                )
                            ),
                            start = 4,
                            end = 9
                        )
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 10.dp),
                    fontSize = 11.sp,
                    color = Color(0xFFBABABA),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
            }
        }
//        Text(
//            text = "9",
//            modifier = Modifier
//                .weight(.25f),
//            fontSize = 14.sp,
//            color = Color.Black,
//            textAlign = TextAlign.Center,
//            fontFamily = FontFamily(
//                Font(R.font.roboto_medium)
//            )
//        )
        Image(
            modifier = Modifier
                .weight(.25f)
                .size(20.dp)
                .clickable {
//                playersList.player?.isSelected = !playersList.player?.isSelected!!
                    if (playersList.player?.isSelected!!) {
                        function(index)

                    } else {
                        if (!isMaxReached && !isMaxPosTypeReached && !(playersList.player.isForeigner == 1 && isForeignCountReached)) function(
                            index
                        )

                    }


                },
            painter = painterResource(id = if (playersList.player?.isSelected!!) R.drawable.iv_remove else R.drawable.iv_add),
            contentDescription = "add"
        )
//                Text(
//                    text = "9",
//                    modifier = Modifier
//                        .weight(.25f),
//                    fontSize = 14.sp,
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(
//                        Font(R.font.roboto_medium)
//                    )
//                )

    }
}