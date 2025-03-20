package com.newagesmb.androidmvvmarchitecture.ui.join_contest

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.ui.TeamCreateSuccessScreen
import com.newagesmb.androidmvvmarchitecture.data.model.response.Players
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.JoinContestViewModel
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.launch

//
// Created by Noushad on 17-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun TeamPreviewScreen(navController: NavHostController, joinContestViewModel: JoinContestViewModel) {
    var isCreated by rememberSaveable { mutableStateOf(false) }
    val createTeamState by joinContestViewModel.createTeam.collectAsState()
    val updateTeamState by joinContestViewModel.updateTeam.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(createTeamState) {
        launch {

            createTeamState?.let {
                when (it) {
                    is Resource.Loading -> {
//                        isLoading = true
                        // Show loading state
                    }

                    is Resource.StartLoading -> {

                        joinContestViewModel.isLoading = true
                    }

                    is Resource.Success -> {

                        joinContestViewModel.isLoading = false
                        if (it.data?.success!!){
                            isCreated = true
                        }


                    }

                    is Resource.Error -> {
                        joinContestViewModel.isLoading = false

                        context?.showToast(it.errorMessage)
                    }
                }
            }
        }
    }
    LaunchedEffect(updateTeamState) {
        launch {

            updateTeamState?.let {
                when (it) {
                    is Resource.Loading -> {
//                        isLoading = true
                        // Show loading state
                    }

                    is Resource.StartLoading -> {

                        joinContestViewModel.isLoading = true
                    }

                    is Resource.Success -> {

                        joinContestViewModel.isLoading = false
                        if (it.data?.success!!){
                            isCreated = true
                        } else {
                            context?.showToast(it.data.message)
                        }


                    }

                    is Resource.Error -> {
                        joinContestViewModel.isLoading = false

                        context?.showToast(it.errorMessage)
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

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
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
                    Text(
                        text = stringResource(id = R.string.create_team),
                        modifier = Modifier
                            .padding(start = 20.dp)
                            .weight(1f),
                        color = Color.White,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        ),
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = Color.White)
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(id = R.string.choose_captain),
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                            .wrapContentWidth(),
                        color = Color.Black,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_medium)
                        ),
                    )

                    Text(
                        text = "C gets 2X points, VC gets 1.5x Points",
                        modifier = Modifier
                            .padding(horizontal = 15.dp, vertical = 5.dp)
                            .wrapContentWidth(),
                        color = Color(0xFF8D8D8D),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_light)
                        ),
                    )
                }

            }
            Row(
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.players),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 10.dp),
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
//                        .wrapContentWidth()
//                        .padding(start = 20.dp),
//                    fontSize = 12.sp,
//                    color = Color(0xFF3B3B3B),
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(
//                        Font(R.font.roboto_regular)
//                    )
//                )


            }
            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp).weight(1f),
                verticalArrangement = Arrangement.Top,

                ) {
                itemsIndexed(joinContestViewModel.selectedPlayersList) { index, item ->
                    SelectedPlayersItem(
                        index,
                        item,
                        function = { id ->
//                        selectedCaptain.value = if (selectedCaptain.value == index) null else index
//                        if (index == selectedViceCaptain.value)
//                            selectedViceCaptain.value = null
//                            joinContestViewModel.selectedPlayersList = joinContestViewModel.selectedPlayersList.map {
//                                it.copy(isCaptain = id == it.id)
//                            }
                            joinContestViewModel.selectedPlayersList.forEachIndexed { i, player ->
                                joinContestViewModel.selectedPlayersList[i] = player.copy(isCaptain = if(player.id == id) 1 else 0 , isViceCaptain = if (id == player.id) 0 else player.isViceCaptain )
                            }
                            joinContestViewModel.setCaptains()
                        },
                        function1 = { id ->

                            joinContestViewModel.selectedPlayersList.forEachIndexed { i, player ->
                                joinContestViewModel.selectedPlayersList[i] = player.copy(isViceCaptain = if(player.id == id) 1 else 0,  isCaptain = if (id == player.id) 0 else player.isCaptain)
                            }
                            joinContestViewModel.setCaptains()
//                            selectedViceCaptain.value =
//                                if (selectedViceCaptain.value == index) null else index
//                            if (index == selectedCaptain.value)
//                                selectedCaptain.value = null
                        })
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {

                Button(

                    onClick = {
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
                        if (joinContestViewModel.isCaptainsSelected){
                            if (joinContestViewModel.teamList.isNullOrEmpty()){
                                joinContestViewModel.createTeam()
                            } else {
                                joinContestViewModel.updateTeam()
                            }
                        }

                    },
                    modifier = Modifier
                        .weight(.5f),
                    colors = ButtonDefaults.buttonColors(

                        backgroundColor = if (joinContestViewModel.isCaptainsSelected) Color(
                            0xFF029634
                        ) else Color(0xFF4F8A63)
                    )
                    //, shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                ) {
                    Text(
                        text = if (joinContestViewModel.teamList.isNullOrEmpty())stringResource(id = R.string.save) else stringResource(id = R.string.update),
                        color = if (joinContestViewModel.isCaptainsSelected)  Color.White else Color(
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

        if (joinContestViewModel.isLoading) {
            CircleLoader(
                color = Color(0xFF0D1A30),
                modifier = Modifier.size(70.dp),
                isVisible = joinContestViewModel.isLoading,
                secondColor = Color(0xFF029634)
            )
        }
        if (isCreated){
            val message = if (joinContestViewModel.teamList.isNotEmpty()){
                 "Team Updated"
            } else {
                stringResource(id = R.string.published_successfully)
            }
            val content = if (joinContestViewModel.teamList.isNotEmpty()){
                "Your team has been updated successfully"
            } else {
                stringResource(id = R.string.team_created)
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0x80000000), shape = RoundedCornerShape(20.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                TeamCreateSuccessScreen(animatedFile = R.raw.success_animation, message = message,content = content) {
                    isCreated = false
                    navController.navigate(AppNavigation.HOME.name) {
                        popUpTo(AppNavigation.TEAM_PREVIEW.name) { inclusive = true }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SelectedPlayersItem(
    i: Int = 0,
    player: Players,
    function: (index: Int) -> Unit,
    function1: (index: Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 5.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Row(
            modifier = Modifier.weight(.65f), verticalAlignment = Alignment.CenterVertically,
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
                    text = player.playerName ?: "",
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

        Row(
            modifier = Modifier
                .weight(.35f)
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(1.dp, Color(0xFF3B3B3B), CircleShape) // Add a gray border
                    .background(
                        if (player.isCaptain == 1) Color.Black else Color.Transparent,
                        CircleShape
                    ) // Transparent background
                    .size(40.dp)
                    .clickable { function(player.id) }
            ) {
                Text(
                    text = "C", modifier = Modifier
                        .padding(10.dp)
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = if (player.isCaptain == 1) Color.White else Color(0xFF3B3B3B),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .border(1.dp, Color(0xFF3B3B3B), CircleShape) // Add a gray border
                    .background(
                        if (player.isViceCaptain == 1) Color.Black else Color.Transparent,
                        CircleShape
                    ) // Transparent background
                    .size(40.dp)
                    .clickable { function1(player.id) }
            ) {
                Text(
                    text = "VC", modifier = Modifier
                        .padding(10.dp)
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = if (player.isViceCaptain == 1) Color.White else Color(0xFF3B3B3B),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
            }

        }
    }


}