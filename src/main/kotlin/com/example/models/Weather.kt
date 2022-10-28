package com.example.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Weather(
    val temperature: Double,
    @SerialName("windspeed") val  windSpeed: Double,
    @SerialName("winddirection") val  windDirection: Int,
    @SerialName("weathercode") val weatherCode: Int,
    val time: String
)
