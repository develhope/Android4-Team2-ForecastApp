package co.develhope.meteoapp.networking.domainmodel

data class Place(
    val city: String? ,
    val weather: String? ,
    val temperature: Int? ,
    val longitude: Double ,
    val latitude: Double ,
    val region: String?
)