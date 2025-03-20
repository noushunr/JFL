package com.newagesmb.androidmvvmarchitecture.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

//
// Created by Noushad on 08-01-2025.
// Copyright (c) 2025 Newagesys. All rights reserved.
//
object CommonUtils {
    @SuppressLint("NewApi")
    fun formatDate(date:String,  writeFormat : String) : String{
        val parsedDate = LocalDate.parse(date) // Parse the date string
        val formatter = DateTimeFormatter.ofPattern(writeFormat) // Define the desired format
        return parsedDate.format(formatter) // Format the date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateNew(date:String, writeFormat : String) : String{
        val instant = Instant.parse(date)
        val formatter = DateTimeFormatter.ofPattern(writeFormat).withZone(ZoneId.systemDefault())

        val formattedDate = formatter.format(instant)
        return formattedDate
    }

    fun generateRandomString(length: Int): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+[]{}|;:',.<>?/`~"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
    fun getFormattedTimeDifference(startDate: String,endDate:String? = null): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val endFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val startDateTime = LocalDateTime.parse(startDate, formatter) // Ensure midnight start
             // Ensure midnight start
            val now = LocalDateTime.now(ZoneId.systemDefault())

            val totalSeconds = ChronoUnit.SECONDS.between(now, startDateTime)
            val days = totalSeconds / (24 * 3600)
            val hours = (totalSeconds % (24 * 3600)) / 3600
            val minutes = (totalSeconds % 3600) / 60
            val seconds = totalSeconds % 60

            if (!endDate.isNullOrEmpty()){
                val endDateTime = LocalDate.parse(endDate, endFormatter)
                val isCompleted = endDateTime.isBefore(LocalDate.now())
                if (isCompleted)
                    return "Completed"
            }

            return when {
                days > 0 -> "${days}d ${hours}h"
                hours > 0 -> "${hours}h ${minutes}m"
                minutes > 0 -> "${minutes}m ${seconds}s"
                seconds > 0 -> "${seconds}s"
                else -> "LIVE"
            }
        } else {
           return formatDate(startDate , "d MMM")
        }

    }

    fun isPastTime(matchDate: String, matchTime: String):Boolean{
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

            // Parse match start time
            return try {
                val matchDateTime = LocalDateTime.parse("$matchDate $matchTime", dateTimeFormatter)
                val currentTime = LocalDateTime.now(ZoneId.systemDefault())
                Log.d("EditAvailability","${currentTime.isBefore(matchDateTime)}")
                // Enable edit before the match start time
                currentTime.isBefore(matchDateTime)
            } catch (e: Exception) {
                false // Handle parsing errors
            }
        } else {
            return false
        }
    }
}