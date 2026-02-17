package com.financiaplus.app.core.network.dto

import com.google.gson.annotations.SerializedName

data class GeoLocationDto(
    @SerializedName("query") val ip: String,
    @SerializedName("city") val city: String,
    @SerializedName("regionName") val region: String,
    @SerializedName("countryCode") val country: String,
    @SerializedName("country") val countryName: String,
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)