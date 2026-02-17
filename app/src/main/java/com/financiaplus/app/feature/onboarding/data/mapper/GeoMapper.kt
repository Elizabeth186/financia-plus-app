package com.financiaplus.app.feature.onboarding.data.mapper

import com.financiaplus.app.core.network.dto.GeoLocationDto
import com.financiaplus.app.feature.onboarding.domain.model.GeoLocation

fun GeoLocationDto.toGeoLocation(): GeoLocation {
    return GeoLocation(
        ip = ip,
        city = city,
        region = region,
        country = country,
        countryName = countryName,
        latitude = latitude,
        longitude = longitude
    )
}