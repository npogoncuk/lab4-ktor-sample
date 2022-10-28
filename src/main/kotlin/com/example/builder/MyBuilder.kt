package com.example.builder

class CurrentWeatherBuilder : Builder {

    private var basUrl = "https://api.open-meteo.com/v1/forecast?"

    override fun setPlace(latitude: Double, longitude: Double): Builder {
        basUrl += "latitude=$latitude&longitude=$longitude&"
        return this
    }

    override fun setCurrentWeather(current: Boolean): Builder {
        basUrl += "current_weather=$current&"
        return this
    }

    override fun setTimeZone(timezone: String): Builder {
        basUrl += "timezone=$timezone&"
        return this
    }

    override fun build(): String {
        return basUrl
    }

}