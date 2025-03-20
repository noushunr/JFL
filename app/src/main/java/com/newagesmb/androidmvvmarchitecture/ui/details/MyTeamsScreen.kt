package com.newagesmb.androidmvvmarchitecture.ui.details

import android.os.Build
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.TeamList
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

//
// Created by Noushad on 02-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun MyTeamsScreen(teamList: TeamList, index: Int = 1,onClick:(teamList : TeamList)->Unit,onEdit:(teamList : TeamList)->Unit) {
    val wkCount = teamList.teamPlayers.filter { it.playerPosition.roleCode == "WK" }.count()
    val batCount = teamList.teamPlayers.filter { it.playerPosition.roleCode == "BAT" }.count()
    val bowlCount = teamList.teamPlayers.filter { it.playerPosition.roleCode == "Bowler" }.count()
    val allRoundCount = teamList.teamPlayers.filter { it.playerPosition.roleCode == "AR" }.count()
    val captain = teamList.teamPlayers.find { it.isCaptain == 1 }
    val viceCaptain = teamList.teamPlayers.find { it.isViceCaptain == 1 }
    Box(


        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .wrapContentHeight()
//            .paint(
//                painter = painterResource(id = R.drawable.my_team_bg),
//                contentScale = ContentScale.FillBounds
//            )
        //.background(color = Color.White)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize() // Ensures the drawable covers the full parent Box
                .paint(
                    painter = painterResource(id = R.drawable.my_team_bg),
                    contentScale = ContentScale.FillBounds // Scales the background to fill the Box
                )
                .padding(10.dp)
                .clickable {
                    onClick(teamList)
//                    onEdit(teamList)
                }
        )
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "#Team $index",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 5.dp),
                    fontSize = 12.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                if (teamList.isEditEnabled){
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "back",
                        modifier = Modifier
                            .padding(end = 5.dp)
                            .clickable {
                                onEdit(teamList)
//                            navController.navigateUp()
                            }

                    )
                }

//                Text(
//                    text = "13 SEP",
//                    modifier = Modifier
//                        .wrapContentWidth()
//                        .padding(end = 5.dp),
//                    fontSize = 14.sp,
//                    color = Color.Black,
//                    textAlign = TextAlign.Center,
//                    fontFamily = FontFamily(
//                        Font(R.font.roboto_regular)
//                    )
//                )

            }
            Divider(
                color = Color(0x90F5F5F5),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 10.dp, vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
//                Image(
//                    painter = painterResource(R.drawable.ic_tournament),
//                    contentDescription = "avatar",
//                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
//                    modifier = Modifier
//                        .size(60.dp)
//                        .clip(CircleShape)                       // clip to the circle shape
//                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)
//                )
                Text(
                    text = "${captain?.player?.playerName} - ", modifier = Modifier
                        .padding(start = 20.dp)
                        .wrapContentWidth(),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_bold)
                    )
                )
                Text(
                    text = "C", modifier = Modifier
                        .padding(start = 5.dp)
                        .wrapContentWidth(),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_bold)
                    )
                )
                Text(
                    text = "${viceCaptain?.player?.playerName} - ", modifier = Modifier
                        .padding(start = 20.dp)
                        .wrapContentWidth(),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_bold)
                    )
                )
//                Image(
//                    painter = painterResource(R.drawable.ic_tournament),
//                    contentDescription = "avatar",
//                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
//                    modifier = Modifier .padding(start = 15.dp)
//                        .size(60.dp)
//                        .clip(CircleShape)                       // clip to the circle shape
//                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)
//                )
                Text(
                    text = "VC", modifier = Modifier
                        .padding(start = 5.dp)
                        .wrapContentWidth(),
                    fontSize = 16.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_bold)
                    )
                )
            }
            Divider(
                color = Color(0x90F5F5F5),
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
                    text = "WK $wkCount", modifier = Modifier
                        .padding(start = 10.dp)
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                Text(
                    text = "BAT $batCount", modifier = Modifier
                        .padding(start = 10.dp)
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                Text(
                    text = "AR $allRoundCount", modifier = Modifier
                        .padding(start = 10.dp)
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )

                Text(
                    text = "BOWL $bowlCount", modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .wrapContentWidth(),
                    fontSize = 14.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
            }
        }
    }
}

fun checkMyTeamEditAvailability(matchDate: String, matchTime: String): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

        // Parse match start time
        return try {
            val matchDateTime = LocalDateTime.parse("$matchDate $matchTime", dateTimeFormatter)
            val editStartTime = matchDateTime.minusMinutes(60)

            val currentTime = LocalDateTime.now(ZoneId.systemDefault())

            Log.d("EditAvailability","${currentTime.isBefore(editStartTime)}")
            // Enable edit before the match start time
            currentTime.isBefore(editStartTime)
        } catch (e: Exception) {
            false // Handle parsing errors
        }
    } else {
        return false
    }

}