package com.example.builder



interface Builder {

    fun setPlace(latitude: Double = 59.3328, longitude: Double = 18.0645): Builder

    // https://api.open-meteo.com/v1/forecast?latitude=50.4422&longitude=30.5367&timezone=auto&current_weather=true
    fun setCurrentWeather(current: Boolean): Builder

    fun setTimeZone(timezone: String = "auto"): Builder

    fun build(): String
}