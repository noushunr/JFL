package com.newagesmb.androidmvvmarchitecture.ui.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.ui.login.LoginViewModel
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.launch

@Composable
@Preview(showBackground = true)
fun ForgotPasswordScreen(navController: NavHostController) {
    val viewModel: ForgotPasswordViewModel = hiltViewModel()
    var isLoading by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val forgotPasswordState by viewModel.forgotPasswordState.collectAsState()
    LaunchedEffect(forgotPasswordState) {
        launch {

            forgotPasswordState.let {
                when (it) {
                    is Resource.Loading -> {
//                        isLoading = true
                        // Show loading state
                    }

                    is Resource.StartLoading -> {

                        isLoading = true
                    }

                    is Resource.Success -> {

                        isLoading = false
                        if (it.data?.success!!) {
                            navController.navigateUp()
                        }
                        context?.showToast(it.data?.message)

                    }

                    is Resource.Error -> {
                        isLoading = false

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
                    color = Color.White

                )
                .padding(10.dp),
        ) {

            Text(
                text = stringResource(id = R.string.forgot_password),
                modifier = Modifier.padding(top = 75.dp, start = 15.dp, end = 22.dp),
                color = Color.Black,
                fontSize = 30.sp,
//            fontFamily = customFontFamily,
                textAlign = TextAlign.Center,

                )

            TextField(
                value = viewModel.email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,

                    imeAction = ImeAction.Next, autoCorrect = false
                ),
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    viewModel.email = it
                    if (viewModel.emailErrorMessage.isNotEmpty()) {
                        viewModel.emailErrorMessage = ""
                    }

                },
                modifier = Modifier
                    .padding(top = 25.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD5D5D5),
                        shape = RoundedCornerShape(10.dp)
                    ),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
//                textStyle = TextStyle(fontSize = 15.sp),


                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                    cursorColor = Color.Black,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.enter_email),
                        color = Color(0xFF9F9A9A),
                        fontSize = 16.sp,
//                        fontFamily = customFontFamily,
                    )
                }


                //                label = { Text(text = stringResource(id = R.string.enter_email), fontSize = 16.sp) }
            )
            if (viewModel.emailErrorMessage.isNotEmpty()) {
                Text(
                    text = viewModel.emailErrorMessage,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(top = 5.dp, start = 25.dp, end = 20.dp),
                    color = Color.Red,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
//                    fontFamily = customFontFamily,

                )
            }

            Button(

                onClick = {

                    viewModel.forgotPassword()
                },
                modifier = Modifier
                    .padding(top = 35.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF029634)
                )
                //, shape = RoundedCornerShape(topEnd = 12.dp, bottomEnd = 12.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.submit),
                    color = Color.White,
                    fontSize = 16.sp,
//                fontFamily = customFontFamily,
                    modifier = Modifier.padding(
                        start = 35.dp,
                        end = 35.dp,
                        top = 10.dp,
                        bottom = 10.dp
                    )
                )

            }
        }
        if (isLoading) {
            CircleLoader(
                color = Color(0xFF0D1A30),
                modifier = Modifier.size(70.dp),
                isVisible = isLoading,
                secondColor = Color(0xFF029634)
            )
        }
    }
}
