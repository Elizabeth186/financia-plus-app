package com.financiaplus.app.core.network.api

import com.financiaplus.app.core.network.dto.GeoLocationDto
import retrofit2.Response
import retrofit2.http.GET

interface GeoLocationApiService {

    @GET("json/")
    suspend fun getGeoLocation(): Response<GeoLocationDto>

}