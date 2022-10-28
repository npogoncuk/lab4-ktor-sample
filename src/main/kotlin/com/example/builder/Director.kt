package com.example.builder

class Director(var builder: Builder) {

    fun makeCurrentWeatherUrl(): String {
        return builder
            .setPlace()
            .setCurrentWeather(true)
            .setTimeZone()
            .build()
    }
}