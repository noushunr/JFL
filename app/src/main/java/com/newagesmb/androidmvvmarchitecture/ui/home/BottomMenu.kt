package com.newagesmb.androidmvvmarchitecture.ui.home
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.navigation.NavigationItem

//
// Created by Noushad on 01-07-2024.
// Copyright (c) 2024 NewAgeSys. All rights reserved.
//
@Composable
fun BottomMenu(
    navController: NavHostController = NavHostController(LocalContext.current)
) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.MyMatches,
//        NavigationItem.Predictions,
        NavigationItem.Winners
    )
    val selectedColor = Color(0XFF029634) // Red color for selected state
    val unselectedColor = Color(0xFFA5A5A5) // Green color for unselected state
    Column {
        Divider(color = Color(0xFFEFEFF0), thickness = 1.dp)
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            items.forEach { screen ->
                val selected = currentRoute == screen.route
                BottomNavigationItem(

                    icon = {
                        Icon(
                            painterResource(id = screen.icon!!),
                            contentDescription = null,
                            modifier = Modifier.size(30.dp),
                            tint = if (selected) selectedColor else unselectedColor
                        )

                    },
                    label = { Text(screen.label!!,
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontSize = 14.sp, color = if (selected) selectedColor else unselectedColor, textAlign = TextAlign.Center, fontFamily = FontFamily(
                            Font(
                            R.font.roboto_regular)
                        )
                    )},
                    selected = currentRoute == screen.route,
                    selectedContentColor = selectedColor,
                    unselectedContentColor = unselectedColor,
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(AppNavigation.SPLASH.name) { inclusive = true }
                        }
                    }
                )
            }
        }
    }

}
