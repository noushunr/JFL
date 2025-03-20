package com.newagesmb.androidmvvmarchitecture.ui.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.newagesmb.androidmvvmarchitecture.R

@Composable
@Preview(showBackground = true)
fun ChangePasswordScreen(){
    var passwordVisible by remember { mutableStateOf(false) }
    Column(

        modifier = Modifier
            .fillMaxSize()

            .background(
                color = Color.White

            )
            .padding(10.dp),
    ) {

        Text(
            text = stringResource(id = R.string.change_password),
            modifier = Modifier.padding(top = 75.dp, start = 15.dp, end = 22.dp),
            color = Color.Black,
            fontSize = 30.sp,
//            fontFamily = customFontFamily,
            textAlign = TextAlign.Center,

            )

        TextField(
            value = "",
            onValueChange = {

            },
            singleLine = true,
            modifier = Modifier
                .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFD5D5D5), shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
//            textStyle = TextStyle(fontSize = 15.sp, fontFamily = customFontFamily),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.password),
                    color = Color(0xFF5E5E5F),
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

        TextField(
            value = "",
            onValueChange = {

            },
            singleLine = true,
            modifier = Modifier
                .padding(top = 15.dp, start = 15.dp, end = 15.dp)
                .fillMaxWidth()
                .border(width = 1.dp, color = Color(0xFFD5D5D5), shape = RoundedCornerShape(10.dp)),
            shape = RoundedCornerShape(10.dp),
//            textStyle = TextStyle(fontSize = 15.sp, fontFamily = customFontFamily),
            placeholder = {
                Text(
                    text = stringResource(id = R.string.confirm_password),
                    color = Color(0xFF5E5E5F),
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
        Button(

            onClick = {

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
}
