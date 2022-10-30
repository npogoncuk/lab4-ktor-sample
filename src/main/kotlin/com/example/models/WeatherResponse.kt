package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val utc_offset_seconds: Int,
    val timezone: String,
    val timezone_abbreviation: String,
    val elevation: Double,
    val current_weather: Weather
) {
}

object WeatherDatabase : Table() {
    val id = integer("id").autoIncrement()
    val latitude = double("latitude")
    val longitude = double("longitude")
    val generationtime_ms = double("generationtime_ms")
    val utc_offset_seconds = integer("utc_offset_seconds")
    val timezone = varchar("timezone", 256)
    val timezone_abbreviation = varchar("timezone_abbreviatio", 256)
    val elevation = double("elevation")
    val temperature = double("temperature")
    val windspeed = double("windspeed")
    val winddirection = double("winddirection")
    val weathercode = integer("weathercode")
    val time = varchar("time", 256)
    override val primaryKey = PrimaryKey(id)
}