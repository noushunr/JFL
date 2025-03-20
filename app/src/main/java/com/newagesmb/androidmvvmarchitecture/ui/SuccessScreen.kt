package com.newagesmb.androidmvvmarchitecture.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.ui.home.AnimatedPreloader
import com.newagesmb.androidmvvmarchitecture.utils.customFontFamily
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//
// Created by Noushad on 15-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@Composable
fun TeamCreateSuccessScreen(animatedFile:Int, message : String = "" ,content : String = "" , onDismiss: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(color = Color.White, shape = RoundedCornerShape(20.dp))
            .padding(16.dp),

        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedPreloader(animatedFile)
            Text(
                text = message,
                modifier = Modifier.padding(top = 10.dp, start = 22.dp, end = 22.dp),
                color = Color(0xFF4B4B4C),
                fontSize = 20.sp,

                textAlign = TextAlign.Center,
                fontFamily = customFontFamily,
            )
            Text(
                text = content,
                modifier = Modifier.padding(top = 10.dp, start = 22.dp, end = 22.dp, bottom = 25.dp),
                color = Color(0xFF4B4B4C),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                fontFamily = customFontFamily,
            )
        }

    }

    coroutineScope.launch {
        delay(3000)
        onDismiss()
    }

}