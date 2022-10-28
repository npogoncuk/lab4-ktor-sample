package com.example.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*

@Serializable
data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val elevation: Int,
    val current_weather: Weather
) {
}

object WeatherDatabase : Table() {
    val id = integer("id").autoIncrement()
    val body = varchar("body", 2048)

    override val primaryKey = PrimaryKey(id)
}