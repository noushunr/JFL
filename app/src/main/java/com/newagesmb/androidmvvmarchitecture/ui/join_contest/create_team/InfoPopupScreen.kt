package com.newagesmb.androidmvvmarchitecture.ui.join_contest.create_team

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.newagesmb.androidmvvmarchitecture.R

//
// Created by Noushad on 18-02-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
@Composable
@Preview(showBackground = true)
fun InfoPopupScreen(showPopup:Boolean=true,infos:MutableList<String>, onDismiss:()->Unit) {
    //
    if (showPopup) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = {
                onDismiss()
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .height(IntrinsicSize.Min) // Ensures equal height for both columns
                    ) {
                        // Left Column (Black background with payment methods)
                        Column(
                            modifier = Modifier
                                .background(Color.Black)
                                .wrapContentWidth() // Takes less space compared to the right section
                                .fillMaxHeight(), // Matches height with the right section
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text("Google Pay", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),  fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            ))
                            Text("PhonePe", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp,vertical = 2.dp),  fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            ))
                            Text("Paytm", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp,vertical = 2.dp),  fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            ))
                            Text("Whatsapp", color = Color.White, fontSize = 10.sp, modifier = Modifier.padding(horizontal = 4.dp,vertical = 2.dp),  fontFamily = FontFamily(
                                Font(R.font.roboto_regular)
                            ))
                        }

                        // Right Section (Red contact number and Blue price info)
                        Column(
                            modifier = Modifier
                                .weight(1f) // Takes more space
                                .fillMaxHeight()
                        ) {
                            // Contact number section
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.Red)
                                    .weight(1f) // Equal height
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("+91 9895269145", color = Color.White, fontSize = 15.sp,  fontFamily = FontFamily(
                                    Font(R.font.roboto_regular)
                                ))
                            }

                            // Price section

                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp)
                            .background(Color.Blue)
                            .wrapContentHeight().padding(5.dp) // Equal height
                        ,
                        contentAlignment = Alignment.Center
                    ) {
//                        Text("Rs.$tournamentPrice/- per players list", color = Color.White, fontSize = 15.sp,  fontFamily = FontFamily(
//                            Font(R.font.roboto_regular)
//                        ))
                    }


//                Image(
//                    painter = painterResource(id = R.drawable.payment_img), // Replace with your image resource
//                    contentDescription = "Popup Image",
//                    modifier = Modifier.fillMaxWidth()
//                )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Please complete the payment using the method above. The team will approve your request only after the payment is made",
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(horizontal = 5.dp, vertical = 2.dp),
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Start,
                        fontFamily = FontFamily(
                            Font(R.font.roboto_regular)
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        onDismiss()
                    },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(
                                0xFF029634
                            )

                        )

                    ) {
                        Text(
                            text = "Ok",
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(horizontal = 5.dp, vertical = 2.dp),
                            fontSize = 14.sp,
                            color = Color.White,
                            textAlign = TextAlign.Start,
                            fontFamily = FontFamily(
                                Font(R.font.roboto_medium)
                            )
                        )
                    }
                }
            }
        }

    }
}
