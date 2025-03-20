package com.newagesmb.androidmvvmarchitecture.ui.details.scorecard

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.MatchPlayers

//
// Created by Noushad on 14-03-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//



@Composable
fun LeagueTableScreen(playerStats : List<MatchPlayers>) {

//    val textMeasurer = TextMeasurer()
//    val maxTextWidth = playerStats.map { textMeasurer.measureTextWidth(it) }.maxOrNull() ?: 0

    val textMeasurer = rememberTextMeasurer()
    var maxWidth by remember { mutableStateOf(0.dp) }
    val listState = rememberLazyListState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Header row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text(
                text = "Players",
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                ),
                modifier = Modifier
                    .width(180.dp)
                    .padding(8.dp)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scrollState)
            ) {
                HorizontalStatRow()
            }
        }

        // Table content
        LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
            items(playerStats) { team ->
                TeamRow(team = team, scrollState = scrollState)
            }
        }
    }
}

@Composable
fun TeamRow(team: MatchPlayers, scrollState: ScrollState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 4.dp)
    ) {
        // Fixed team name column
        Row(
            modifier = Modifier
                .width(180.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = team.teamPlayers?.playerName!!,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_medium)
                ),
            )
        }

        // Scrollable stats row
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState, reverseScrolling = false)
        ) {
            val stats = listOf(team.runs, team.six, team.four, team.wickets, team.catches, team.matchPoint)
            stats.forEach { stat ->
                Text(
                    text = stat.toString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color(0xFF3B3B3B),
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(60.dp)
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
fun HorizontalStatRow() {
    Row {
        val headers = listOf("R", "6", "4", "WK", "C", "PTS")
        headers.forEach { header ->
            Text(
                text = header,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .width(60.dp)
                    .padding(8.dp)
            )
        }
    }
}
@Composable
fun TextMeasurer(): Int {
    val textMeasurer = LocalContext.current.resources.displayMetrics.density * 20
    return textMeasurer.toInt()
}
data class Team(
    val name: String,
    val played: Int,
    val won: Int,
    val draw: Int,
    val lost: Int,
    val points: Int,
    val goalsFor: Int
)
