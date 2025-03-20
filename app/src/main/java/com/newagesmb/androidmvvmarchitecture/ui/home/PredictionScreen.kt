package com.newagesmb.androidmvvmarchitecture.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.newagesmb.androidmvvmarchitecture.R

//
// Created by Noushad on 02-01-2025.
// Copyright (c) 2025 NewAgesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun PredictionScreen() {
    Box(modifier = Modifier.background(color = Color.White).fillMaxSize()) {
        Text(
            text = "Coming Soon", modifier = Modifier
                .wrapContentWidth().align(Alignment.Center),
            fontSize = 14.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(R.font.roboto_regular)
            ))
    }
}