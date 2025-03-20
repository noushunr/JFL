package com.newagesmb.androidmvvmarchitecture.ui.home

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet

import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.model.response.Tournaments
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.navigation.NavigationItem
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.CommonUtils
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(showBackground = true)
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val pagerState = rememberPagerState { 1 }
    val viewModel: HomeViewModel = hiltViewModel()
    BackHandler {
        // Check if context is an activity
        if (context is Activity) {
            context.finish()
        }
    }
    val systemUiController = rememberSystemUiController()

    // Change the status bar color
    systemUiController.setStatusBarColor(
        color = Color(0xFF029634), // Replace with your desired color
        darkIcons = false    // Adjust for dark or light icons
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val tournamentList =
        remember { mutableStateListOf<Tournaments>().apply { addAll(emptyList()) } }
    var isLoading by remember { mutableStateOf(false) }
    LaunchedEffect(drawerState.isOpen) {
        systemUiController.setStatusBarColor(
            color = Color(0xFF029634), // Replace with your desired color
            darkIcons = false    // Adjust for dark or light icons
        )
    }
    LaunchedEffect(Unit) {
        launch {
            viewModel.getTournament()
            viewModel.tournamentLists.collectLatest {
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
                        if (!it?.data?.data?.tournaments.isNullOrEmpty()) {
                            tournamentList.apply {
                                addAll(it.data?.data?.tournaments!!)
                            }
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
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                ) {
                    Column() {
                        DrawerHeader(viewModel)
                        DrawerContent { item ->
                            if (item == "Logout"){
                                viewModel.clearSession {
                                    navController.navigate(AppNavigation.LOGIN.name){
                                        popUpTo(AppNavigation.HOME.name){
                                            inclusive = true
                                        }
                                    }
                                }
                            }
                            // Handle item clicks here
                        }
                    }

                }

            }

        }
    ) {
        Scaffold(
            topBar = {
                MyTopAppBar(drawerState)
//                TopAppBar(
//                    title = { Text(text = "My App") },
//                    navigationIcon = {
//                        IconButton(onClick = { drawerState.open() }) {
//                            Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
//                        }
//                    }
//                )
            },
            content = { innerPadding ->
                Log.d("HomeScreenStarted", "Yes")
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color(0xFF029634))
                        .padding(innerPadding)

                ) {
                    Column(

                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = Color(0xFF029634)
                            ),
                    ) {

                        LazyColumn(

                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    color = Color(0xFFE6E6E6)
                                ),
                            verticalArrangement = Arrangement.Top,

                            ) {
                            item {
                                Column {
                                    HorizontalPager(

                                        state = pagerState, // State to keep track of the current page
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .padding(5.dp)
                                    ) { page ->
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .padding(16.dp),
                                        ) {

                                            Image(
                                                painter = painterResource(R.drawable.img2),
                                                contentDescription = "avatar",


                                                contentScale = ContentScale.FillWidth,            // crop the image if it's not a square
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .wrapContentHeight()
                                                    .clip(RoundedCornerShape(15.dp))
                                            )
                                        }
                                    }

                                    Text(
                                        text = "Upcoming Tournaments",
                                        modifier = Modifier
                                            .wrapContentWidth()
                                            .padding(start = 22.dp, top = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(
                                            Font(R.font.roboto_bold)
                                        )
                                    )
                                }
                            }
                            items(tournamentList, key = { it.id }) { item ->
                                TournamentsItem(item, navController)
                            }
                        }
                    }
                }
            }
        )

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

@Composable
fun TournamentsItem(
    tournaments: Tournaments? = null,
    navController: NavHostController,
    isMyMatches: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 22.dp, vertical = 10.dp)
            .clickable {
                Log.d("Clicked","yes")
                var type = if (tournaments?.tournamentType?.equals("domestic")!!) 0 else 1
                if (isMyMatches) {
                    navController.navigate("${AppNavigation.MY_CONTEST_DETAILS.name}/${tournaments?.id}"){
                        launchSingleTop = true // Ensures the same destination is not created multiple times
                    }
                } else {
                    navController.navigate(
                        "${AppNavigation.PRICE_POOL.name}/${tournaments?.id}/$type"
                    ){
                        launchSingleTop = true // Ensures the same destination is not created multiple times
                    }
                }
            }
        //.background(color = Color.White)
    ) {
        Column() {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "${tournaments?.tournamentName}",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 5.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                Text(
                    text = CommonUtils.formatDate(tournaments?.startDate ?: "2025-03-21", "d MMM"),
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 5.dp),
                    fontSize = 14.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )

            }
            Divider(
                color = Color(0xFFE6E4E4),
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
                Image(
                    painter = painterResource(R.drawable.ic_tournament),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = CommonUtils.getFormattedTimeDifference("${tournaments?.startDate!!} ${tournaments?.startTime!!}",tournaments.endDate),
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontSize = 14.sp,
                        color = Color(0xFFDC0000),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_medium)
                        )
                    )
                    Text(
                        text =CommonUtils.formatDate(
                            tournaments?.startDate ?: "2025-03-21", "d MMM "),
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontSize = 11.sp,
                        color = Color(0xFFBABABA),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(end = 5.dp)
                ) {
                    Text(
                        text = "Entry",
                        modifier = Modifier
                            .wrapContentWidth(),
                        fontSize = 14.sp,
                        color = Color(0xFF4D4D4D),
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        )
                    )
                    if (!isMyMatches) {
                        Button(

                            onClick = {

//                          isLoading = true
                            },
                            modifier = Modifier

                                .wrapContentHeight()
                                .padding(vertical = 5.dp)
                                .align(Alignment.CenterHorizontally),

                            shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF029634)
                            )
                            //, shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
                        ) {
                            Text(
                                text = "₹${tournaments?.price}",
                                color = Color.White,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(
                                    Font(R.font.roboto_medium)
                                ),
//                fontFamily = customFontFamily,
                                modifier = Modifier.padding(
                                    start = 5.dp,
                                    end = 5.dp,
                                    top = 2.dp,
                                    bottom = 2.dp
                                )
                            )

                        }
                    } else {
                        Text(
                            text = "₹${tournaments?.price}",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_medium)
                            ),
//                fontFamily = customFontFamily,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(top = 10.dp)
                        )
                    }
                }
            }

            Divider(
                color = Color(0xFFE6E4E4),
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
                    text = "${tournaments?.tournamentPlayersCount} Players",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(start = 5.dp),
                    fontSize = 14.sp,
                    color = Color(0xFF8D8D8D),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )
                Text(
                    text = "${tournaments?.userTournamentTeamsCount} Contest",
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(end = 5.dp),
                    fontSize = 14.sp,
                    color = Color(0xFF8D8D8D),
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.roboto_regular)
                    )
                )

            }
        }
    }

}

@Composable
fun MyTopAppBar(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars),
        title = {
            Text(
                text = "JFL",
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 5.dp),
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(
                    Font(R.font.roboto_regular)
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { /* Handle navigation icon click */
                scope.launch {
                    drawerState.open()
                }
            }) {
                Image(
                    painter = painterResource(R.drawable.cricket),
                    contentDescription = "avatar",
                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)                       // clip to the circle shape
                        .border(2.dp, Color.White, CircleShape)   // add a border (optional)
                )
            }
        },
        actions = {
            // Menu item 1: Settings icon
            IconButton(onClick = { /* Handle settings click */ }) {
                Image(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = "avatar",

                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                    modifier = Modifier
                        .size(25.dp)
                    // add a border (optional)
                )
//                Icon(imageVector = Icons.Default.Notifications, contentDescription = "Settings", tint = Color.White)
            }


        },
        backgroundColor = Color(0xFF029634)
    )
}

@Composable
fun DrawerHeader(viewModel : HomeViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(top = 24.dp, bottom = 40.dp), // Padding to separate from items
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.cricket), // Replace with your image
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .padding(start = 35.dp, top = 10.dp, bottom = 10.dp)
        )
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = viewModel.userName,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 15.dp),
                fontSize = 18.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(
                    Font(R.font.roboto_medium)
                )
            )
//            Text(
//                text = "4541245",
//                modifier = Modifier
//                    .wrapContentWidth()
//                    .padding(start = 15.dp, top = 3.dp),
//                fontSize = 14.sp,
//                color = Color.White,
//                textAlign = TextAlign.Center,
//                fontFamily = FontFamily(
//                    Font(R.font.roboto_regular)
//                )
//            )
        }

    }
}

@Composable
fun DrawerContent(onItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        DrawerItem(label = "My Profile", onItemClicked)
        DrawerItem(label = "Help & Support", onItemClicked)
        DrawerItem(label = "About Us", onItemClicked)
        DrawerItem(label = "Settings", onItemClicked)
        DrawerItem(label = "Logout", onItemClicked)
    }
}

@Composable
fun DrawerItem(label: String, onItemClicked: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 10.dp, end = 10.dp)
            .background(color = Color(0xFFECEBEB))
    ) {
        Text(
            text = label,
            modifier = Modifier
                .wrapContentWidth()
                .padding(15.dp)
                .clickable { onItemClicked(label) },
            fontSize = 18.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(R.font.roboto_medium)
            )
        )
    }
//    Text(
//        text = label,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//            .clickable { onItemClicked(label) },
//        fontSize = 18.sp,
//        color = Color.Black
//    )
}