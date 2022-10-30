package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.Weather
import com.example.models.WeatherDatabase
import com.example.models.WeatherResponse
import org.jetbrains.exposed.sql.*

interface DAOFacade {
    suspend fun allWeatherResponses(): List<WeatherResponse>
    suspend fun weatherResponse(id: Int): WeatherResponse?
    suspend fun addNewWeatherResponse(
        latitude: Double,
        longitude: Double,
        generationtime_ms: Double,
        utc_offset_seconds: Int,
        timezone: String,
        timezone_abbreviation: String,
        elevation: Double,
        temperature: Double,
        windSpeed: Double,
        windDirection: Double,
        weatherCode: Int,
        time: String) :WeatherResponse?
    suspend fun deleteWeatherResponse(id: Int): Boolean
}

class DAOFacadeImpl : DAOFacade {

    private fun resultRowToWeatherResponse(row: ResultRow) = WeatherResponse(
        latitude = row[WeatherDatabase.latitude],
        longitude = row[WeatherDatabase.longitude],
        generationtime_ms = row[WeatherDatabase.generationtime_ms],
        utc_offset_seconds = row[WeatherDatabase.utc_offset_seconds],
        timezone = row[WeatherDatabase.timezone],
        elevation = row[WeatherDatabase.elevation],
        current_weather = Weather(
            temperature = row[WeatherDatabase.temperature],
            windSpeed = row[WeatherDatabase.windspeed],
            windDirection = row[WeatherDatabase.winddirection],
            weatherCode = row[WeatherDatabase.weathercode],
            time = row[WeatherDatabase.time]
        ),
        timezone_abbreviation = row[WeatherDatabase.timezone_abbreviation]
    )

    override suspend fun allWeatherResponses(): List<WeatherResponse> = dbQuery {
        WeatherDatabase.selectAll().map(::resultRowToWeatherResponse)
    }

    override suspend fun weatherResponse(id: Int): WeatherResponse? = dbQuery {
        WeatherDatabase
            .select { WeatherDatabase.id eq id }
            .map(::resultRowToWeatherResponse)
            .singleOrNull()
    }

    override suspend fun addNewWeatherResponse(
        latitude: Double,
        longitude: Double,
        generationtime_ms: Double,
        utc_offset_seconds: Int,
        timezone: String,
        timezone_abbreviation: String,
        elevation: Double,
        temperature: Double,
        windSpeed: Double,
        windDirection: Double,
        weatherCode: Int,
        time: String) = dbQuery {
        val insertStatement = WeatherDatabase.insert {
            it[WeatherDatabase.latitude] = latitude
            it[WeatherDatabase.longitude] = longitude
            it[WeatherDatabase.generationtime_ms] = generationtime_ms
            it[WeatherDatabase.utc_offset_seconds] = utc_offset_seconds
            it[WeatherDatabase.timezone] = timezone
            it[WeatherDatabase.timezone_abbreviation] = timezone_abbreviation
            it[WeatherDatabase.elevation] = elevation
            it[WeatherDatabase.temperature] = temperature
            it[WeatherDatabase.windspeed] = windSpeed
            it[WeatherDatabase.winddirection] = windDirection
            it[WeatherDatabase.weathercode] = weatherCode
            it[WeatherDatabase.time] = time

        }
        //insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToWeatherResponse)
        null
    }

    override suspend fun deleteWeatherResponse(id: Int): Boolean = dbQuery {
        WeatherDatabase.deleteWhere { WeatherDatabase.id eq id } > 0
    }

}