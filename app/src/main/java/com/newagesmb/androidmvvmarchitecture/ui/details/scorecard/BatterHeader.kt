package com.newagesmb.androidmvvmarchitecture.ui.details.scorecard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.MatchPlayers

//
// Created by Noushad on 18-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun BattingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Batter",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color(0xFF3B3B3B),
            fontFamily = FontFamily(
                Font(R.font.roboto_regular)
            ),
            modifier = Modifier.weight(1.5f)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "R",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("B", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                "4s",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Text(
                "6s",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )

            Text(
                "Point",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("SR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}
@Composable
@Preview(showBackground = true)
fun BattingStats(score: MatchPlayers) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${score.teamPlayers?.playerName}",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color(0xFF3B3B3B),
            fontFamily = FontFamily(
                Font(R.font.roboto_medium)
            ),
            modifier = Modifier.weight(1.5f)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "${score.runs}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("B", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                "${score.four}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Text(
                "${score.six}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Text(
                "${score.matchPoint}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("SR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BowlingHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Bowler",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color(0xFF3B3B3B),
            fontFamily = FontFamily(
                Font(R.font.roboto_regular)
            ),
            modifier = Modifier.weight(1.5f)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "M",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("B", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                "W",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Text(
                "C",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )

            Text(
                "Point",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("SR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun BowlingStats(score: MatchPlayers) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${score.teamPlayers?.playerName}",
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color(0xFF3B3B3B),
            fontFamily = FontFamily(
                Font(R.font.roboto_medium)
            ),
            modifier = Modifier.weight(1.5f)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                "${score.maidenOvers}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("B", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(
                "${score.wickets}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Text(
                "${score.catches}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
            Text(
                "${score.matchPoint}",
                modifier = Modifier.padding(horizontal = 2.dp),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                color = Color(0xFF3B3B3B),
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
//            Text("SR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}