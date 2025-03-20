package com.newagesmb.androidmvvmarchitecture.ui.splash

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
//import androidx.compose.runtime.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.login.LoginViewModel
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.launch

@Composable
@Preview(showBackground = true)
fun SplashScreen(navController : NavHostController = NavHostController(LocalContext.current)) {
//    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.isLoggedIn.collectAsState(false)
    var startAnimation by remember { mutableStateOf(true) }
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1500
        ), label = ""
    )
    val offsetY by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 500.dp,
        animationSpec = tween(
            durationMillis = 1500,
            easing = EaseInOut
        ), label = ""
    )

    val scale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 4f,
        animationSpec = tween(
            durationMillis = 1500,
            easing = EaseInOut
        ), label = ""
    )
//    LaunchedEffect(loginState) {
//        launch {
//            delay(2000)
//            loginState.let {
//                if (it){
//                    navController.navigate(AppNavigation.HOME.name){
//                        popUpTo(AppNavigation.SPLASH.name){
//                            inclusive = true
//                        }
//                    }
//                } else {
//                    navController.navigate(AppNavigation.LOGIN.name){
//                        popUpTo(AppNavigation.SPLASH.name){
//                            inclusive = true
//                        }
//                    }
//                }
//            }
//
//
//        }
//    }
    LaunchedEffect(Unit) {


//        delay(500) // Delay for animation start
//        visible = true
        delay(3000) // Delay for splash screen duration
//        visible = false
//        delay(500)
        loginState.let {
            if (it){
                navController.navigate(AppNavigation.HOME.name){
                    popUpTo(AppNavigation.SPLASH.name){
                        inclusive = true
                    }
                }
            } else {
                navController.navigate(AppNavigation.LOGIN.name){
                    popUpTo(AppNavigation.SPLASH.name){
                        inclusive = true
                    }
                }
            }
        }

    }
//    Column(
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                color = Color(0xFF029634)
//
//            ),
//    ) {
//
//        Text(text = "JFL",   modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 80.dp)
//            .align(Alignment.CenterHorizontally), fontSize = 48.sp, color = Color.White, textAlign = TextAlign.Center, fontFamily = FontFamily(Font(R.font.roboto_black)) )
////        Image(painter = painterResource(id = R.drawable.jfl_splash),contentDescription = "Splash", modifier = Modifier
////            .fillMaxWidth()
////            .padding(top = 30.dp))
//        Image(
//            painter = painterResource(id = R.drawable.jfl_splash),
//            contentDescription = "Splash",
//            contentScale = ContentScale.Crop, // Center-crop the image
//            modifier = Modifier
//                .fillMaxWidth()
//                .align(Alignment.BottomCenter) // Align image at the bottom
//        )
//
//    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF029634))
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "jallu's fantasy league",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp),
                fontSize = 40.sp,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.monotype_corsiva_regular))
            )
//            TypewriterText(text = "JFL")
        }

        Column(modifier = Modifier.fillMaxWidth()
            .alpha(alphaAnim.value)
            .align(Alignment.BottomCenter)
            .offset { IntOffset(0, offsetY.roundToPx()) }
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.jfl_splash),
                contentDescription = "Splash",
                contentScale = ContentScale.Crop, // Center-crop the image
                modifier = Modifier
                    .fillMaxWidth()
                     // Align image at the bottom
            )

        }

    }
}

@Composable
fun TypewriterText(
    text: String,
    typingSpeed: Long = 500L // Speed in milliseconds between characters
) {
    var visibleText by remember { mutableStateOf("") }

    LaunchedEffect(text) {
        visibleText = "" // Reset text when it changes
        text.forEachIndexed { index, _ ->
            visibleText = text.substring(0, index + 1)
            delay(typingSpeed)
        }
    }

    Text(
        text = visibleText,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 100.dp),
        fontSize = 48.sp,
        color = Color.White,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(Font(R.font.roboto_italic))
    )


}