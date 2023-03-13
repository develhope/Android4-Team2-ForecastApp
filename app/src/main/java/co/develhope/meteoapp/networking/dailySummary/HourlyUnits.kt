package co.develhope.meteoapp.networking.dailySummary

data class HourlyUnits(
    val rain: String,
    val showers: String,
    val snowfall: String,
    val temperature_2m: String,
    val time: String,
    val weathercode: String,
    val windspeed_10m: String
)