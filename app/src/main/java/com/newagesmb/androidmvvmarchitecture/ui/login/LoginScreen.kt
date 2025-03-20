package com.newagesmb.androidmvvmarchitecture.ui.login

import android.os.Build
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
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.newagesmb.androidmvvmarchitecture.R
import com.newagesmb.androidmvvmarchitecture.data.remote.nework.Resource
import com.newagesmb.androidmvvmarchitecture.navigation.AppNavigation
import com.newagesmb.androidmvvmarchitecture.utils.CircleLoader
import com.newagesmb.androidmvvmarchitecture.utils.ExtensionFunctions.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.format.TextStyle
import java.util.Locale

@Composable
@Preview(showBackground = true)
fun LoginScreen(navController: NavHostController) {
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current
    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginState by loginViewModel.loginState.collectAsState()
    LaunchedEffect(loginState) {
        launch {

            loginState.let {
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
                            loginViewModel.loginState.value
                            navController.navigate(AppNavigation.HOME.name) {
                                popUpTo(AppNavigation.LOGIN.name) {
                                    inclusive = true
                                }
                            }
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
                ),
        ) {
            val paddingValues = WindowInsets.systemBars.asPaddingValues()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(paddingValues.calculateTopPadding())
                    .background(color = Color(0xFF029634))
            )
            Text(
                text = stringResource(id = R.string.login),
                modifier = Modifier.padding(top = 75.dp, start = 15.dp, end = 22.dp),
                color = Color.Black,
                fontSize = 30.sp,
//            fontFamily = customFontFamily,
                textAlign = TextAlign.Center,

                )

            Text(
                text = stringResource(id = R.string.please_sign_in),
                modifier = Modifier.padding(top = 15.dp, start = 15.dp),
                color = Color(0xFF8D8D8D),
                fontSize = 16.sp,
//            fontFamily = customFontFamily,
                textAlign = TextAlign.Center,

                )

            TextField(
                value = loginViewModel.email,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,

                    imeAction = ImeAction.Next, autoCorrect = false
                ),
                visualTransformation = VisualTransformation.None,
                onValueChange = {
                    loginViewModel.email = it
                    if (loginViewModel.emailErrorMessage.isNotEmpty()) {
                        loginViewModel.emailErrorMessage = ""
                    }
                    //                viewModel.email = it
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
                        text = stringResource(id = R.string.username),
                        color = Color(0xFF9F9A9A),
                        fontSize = 16.sp,
//                        fontFamily = customFontFamily,
                    )
                }


                //                label = { Text(text = stringResource(id = R.string.enter_email), fontSize = 16.sp) }
            )

            if (loginViewModel.emailErrorMessage.isNotEmpty()) {
                Text(
                    text = loginViewModel.emailErrorMessage,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(top = 5.dp, start = 25.dp, end = 20.dp),
                    color = Color.Red,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
//                    fontFamily = customFontFamily,

                )
            }

            TextField(
                value = loginViewModel.password,
                onValueChange = {

                    loginViewModel.password = it
                    if (loginViewModel.passwordErrorMessage.isNotEmpty()) {
                        loginViewModel.passwordErrorMessage = ""
                    }
                },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD5D5D5),
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(10.dp),
//            textStyle = TextStyle(fontSize = 15.sp, fontFamily = customFontFamily),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.password),
                        color = Color(0xFF9F9A9A),
                        fontSize = 16.sp,
//                    fontFamily = customFontFamily,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White,
                    disabledTextColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black,
                    focusedLabelColor = Color(0xFF5E5E5F),
                    unfocusedLabelColor = Color(0xFF5E5E5F),
                    cursorColor = Color.Black,
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
            )
            if (loginViewModel.passwordErrorMessage.isNotEmpty()) {
                Text(
                    text = loginViewModel.passwordErrorMessage,
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(top = 5.dp, start = 25.dp, end = 20.dp),
                    color = Color.Red,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
//                    fontFamily = customFontFamily,

                )
            }
            Text(
                text = stringResource(id = R.string.forgot_password).toUpperCase(Locale.getDefault()),
                modifier = Modifier
                    .padding(top = 15.dp, start = 15.dp, end = 22.dp)
                    .align(Alignment.End)
                    .clickable { navController.navigate(AppNavigation.FORGOT_PASSWORD.name) },
                color = Color.Black,
                fontSize = 16.sp,
//            fontFamily = customFontFamily,
                textAlign = TextAlign.Center,


                )

            Button(

                onClick = {

                    loginViewModel.login()
//                          isLoading = true
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
                    text = stringResource(id = R.string.login),
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

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp, start = 15.dp, end = 22.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.dont_have_account),

                    color = Color(0xFF85848B),
                    fontSize = 16.sp,
//            fontFamily = customFontFamily,
                    textAlign = TextAlign.Center,

                    )
                Text(
                    text = stringResource(id = R.string.sign_up),
                    color = Color(0xFF029634),
                    fontSize = 16.sp,
//            fontFamily = customFontFamily,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.clickable {
                        navController.navigate(AppNavigation.REGISTER.name)
                    }


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