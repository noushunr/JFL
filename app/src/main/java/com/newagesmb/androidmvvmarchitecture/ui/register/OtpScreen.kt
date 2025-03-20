package com.newagesmb.androidmvvmarchitecture.ui.register

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.newagesmb.androidmvvmarchitecture.R
import java.time.format.TextStyle

@Composable
@Preview(showBackground = true)
fun OTPScreen(){
    val otpLength = 4
    val otpValues = remember { MutableList(otpLength) { mutableStateOf("") } }
    val focusRequesters = remember { List(otpLength) { FocusRequester() } }
    Column(

        modifier = Modifier
            .fillMaxSize()

            .background(
                color = Color.White

            )
            .padding(10.dp),
    ) {

        Text(
            text = stringResource(id = R.string.OTP),
            modifier = Modifier.padding(top = 75.dp, start = 15.dp, end = 22.dp),
            color = Color.Black,
            fontSize = 30.sp,
//            fontFamily = customFontFamily,
            textAlign = TextAlign.Center,

            )

        Text(
            text = stringResource(id = R.string.enter_otp_label),
            modifier = Modifier.padding(top = 15.dp, start = 15.dp),
            color = Color(0xFF8D8D8D),
            fontSize = 16.sp,
//            fontFamily = customFontFamily,
            textAlign = TextAlign.Start,

            )

        Row(
            modifier = Modifier
                .padding(top = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until otpLength) {
                TextField(
                    value = otpValues[i].value,

                    onValueChange = { value ->
                        Log.d("Value Entered", value)
                        if (value.length <= 1 && value.isDigitsOnly()) {
                            otpValues[i].value = value
                            if (value.isNotEmpty() && i < otpLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        } else if (value.length <= 2 && value.isDigitsOnly())  {
                            val num1 = value.toInt().div(10)
                            val num2 = value.toInt().mod(10)
                            if ("$num1"==otpValues[i].value)
                                otpValues[i].value = "$num2"
                            else
                                otpValues[i].value = "$num1"

                            if (value.isNotEmpty() && i < otpLength - 1) {
                                focusRequesters[i + 1].requestFocus()
                            }
                        }
                        if (otpValues.size == 4) {
                            var otp = ""
                            otpValues.forEach {
                                otp += it.value

                            }

                            if (otp.trim().length == 4) {

                            }
                            Log.d("OTP", otp)
                        }
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier
                        .padding(start = 4.dp, end = 4.dp)
                        .width(50.dp)
                        .border(width = 1.dp, color = Color(0xFFD5D5D5), shape = RoundedCornerShape(10.dp))
                        .focusRequester(focusRequesters[i])
                        .onFocusChanged { focusState ->
//                                if (focusState.isFocused && otpValues[i].value.isNotEmpty()) {
//                                    otpValues[i].value = ""
//                                }
                        },

                    shape = RoundedCornerShape(8.dp),
//                    textStyle = TextStyle(
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Center,
////                        fontFamily = customFontFamily,
//                    ),

                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color(0xFF5E5E5F),
                        unfocusedLabelColor = Color(0xFF5E5E5F),
                        textColor = Color.Black,
                        cursorColor = Color.Black,

                        ),


//                        label = { Text(text = stringResource(id = R.string.full_name_), fontSize = 15.sp) }
                )
            }
        }
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
                text = stringResource(id = R.string.send),
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
        Text(
            text = stringResource(id = R.string.resend),
            modifier = Modifier.padding(top = 25.dp).fillMaxWidth(),

            color = Color(0xFF676767),
            fontSize = 16.sp,
//            fontFamily = customFontFamily,
            textAlign = TextAlign.Center,

            )
    }
}