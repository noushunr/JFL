package com.newagesmb.androidmvvmarchitecture.ui.join_contest.price

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavHostController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.PricePool
import com.newagesmb.androidmvvmarchitecture.data.model.response.TournamentPrizeMoney
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team.JoinContestViewModel
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily
import kotlinx.coroutines.flow.collectLatest

//
// Created by Noushad on 05-12-2024.
// Copyright (c) 2024 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun PricePoolScreen(
    tournamentId: Int = 0,
    tournamentType: Int = 0,
    navController: NavHostController,
    joinContestViewModel: JoinContestViewModel
) {
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    LaunchedEffect(tournamentId ){
        joinContestViewModel.prizeList = listOf()
        joinContestViewModel.tournamentName = ""
        joinContestViewModel.totalPrizeMoney = ""
        joinContestViewModel.price = ""
        Log.d("PricePool","yes")
        joinContestViewModel.getTournamentPrize(tournamentId)
        joinContestViewModel.prizeMoney.collectLatest {
            when (it) {
                is Resource.Loading -> {

                    // Show loading state
                }

                is Resource.StartLoading -> {

                    isLoading = true
                }

                is Resource.Success -> {

                    isLoading = false
                    // Handle success state
                }

                is Resource.Error -> {
                    isLoading = false
                    context.showToast(it?.errorMessage)
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
                    color = Color.White
                ),
        ) {
            val paddingValues = WindowInsets.systemBars.asPaddingValues()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(paddingValues.calculateTopPadding())
                    .background(color = Color(0xFF029634))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = Color(0xFF029634))
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
                    text = joinContestViewModel.tournamentName,
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
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.price_pool),
                        modifier = Modifier
                            .padding(start = 10.dp),
                        color = Color(0xFF8D8D8D),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light,
                        fontFamily = customFontFamily,
                    )
                    Text(
                        text = joinContestViewModel.totalPrizeMoney,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 5.dp),
                        color = Color.Black,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Medium,
                        fontFamily = customFontFamily,
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(end = 5.dp, top = 10.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = stringResource(id = R.string.entry_fee),
                        modifier = Modifier
                            .padding(end = 10.dp),
                        color = Color(0xFF8D8D8D),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Light,
                        fontFamily = customFontFamily,
                    )
                    if (!joinContestViewModel.price.isNullOrEmpty()){
                        Text(
                            text = "${joinContestViewModel.price.toDouble().toInt()}/-",
                            modifier = Modifier
                                .padding(end = 10.dp, top = 5.dp),
                            color = Color.Black,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.Medium,
                            fontFamily = customFontFamily,
                        )
                    }

                }

            }
            Button(

                onClick = {
                    var price = if (!joinContestViewModel.price.isNullOrEmpty()) joinContestViewModel.price.toDouble().toInt().toString() else ""

                    val type = "empty"
                    joinContestViewModel.clearData()
                    navController.navigate("${AppNavigation.CREATE_TEAM.name}/$tournamentId/$tournamentType/$price/$type")
//                          isLoading = true
                },
                modifier = Modifier
                    .padding(top = 15.dp, start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(5.dp), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black
                )
                //, shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
            ) {
                var price = if (!joinContestViewModel.price.isNullOrEmpty()) joinContestViewModel.price.toDouble().toInt() else ""
                Text(
                    text = "Join $price",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = customFontFamily,
                    modifier = Modifier.padding(
                        start = 35.dp,
                        end = 35.dp,
                        top = 5.dp,
                        bottom = 5.dp
                    )
                )

            }

            Divider(
                color = Color(0xFFE6E4E4),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 25.dp, end = 10.dp, top = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cil_badge),
                    contentDescription = null, // Provide a description for accessibility
                    tint = Color.Unspecified, // Use this to keep the original color
                    modifier = Modifier.size(20.dp) // Adjust the size as needed
                )
                Text(
                    text = "Winnings",
                    modifier = Modifier
                        .padding(start = 5.dp, end = 10.dp),
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    fontFamily = customFontFamily,
                )
            }

            Divider(
                color = Color(0xFFE6E4E4),
                thickness = 2.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(id = R.string.rank),
                    modifier = Modifier
                        .padding(start = 10.dp),
                    color = Color(0xFF8D8D8D),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    fontFamily = customFontFamily,
                )
                Text(
                    text = stringResource(id = R.string.winnings),
                    modifier = Modifier
                        .padding(end = 10.dp),
                    color = Color(0xFF8D8D8D),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Normal,
                    fontFamily = customFontFamily,
                )

            }
            Divider(
                color = Color(0xFFE6E4E4),
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                items(joinContestViewModel.prizeList) {
                    PricePoolItem(pricePool = it)
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
fun PricePoolItem(pricePool: TournamentPrizeMoney) {
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            var rank = "${pricePool.startPosition}"
            if (pricePool.startPosition!=pricePool.endPosition)
                rank += " - ${pricePool.endPosition}"

            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF8D8D8D))) {
                        append("#")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append(" $rank")
                    }
                },
                modifier = Modifier
                    .padding(start = 10.dp),
//                color = Color(0xFF8D8D8D),
                fontSize = 13.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
//                fontFamily = customFontFamily,
            )
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Color(0xFF8D8D8D))) {
                        append("₹")
                    }
                    withStyle(style = SpanStyle(color = Color.Black)) {
                        append("${pricePool.prizeMoney}")
                    }
                },
                modifier = Modifier
                    .padding(end = 10.dp),
//                color = Color(0xFF8D8D8D),
                fontSize = 13.sp,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
//                fontFamily = customFontFamily,
            )

        }
        Divider(
            color = Color(0xFFE6E4E4),
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 5.dp)
        )
    }
}