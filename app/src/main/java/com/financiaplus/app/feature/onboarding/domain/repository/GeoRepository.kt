package com.financiaplus.app.feature.onboarding.domain.repository

import com.financiaplus.app.feature.onboarding.domain.model.GeoLocation

interface GeoRepository {
    suspend fun getGeoLocation(): Result<GeoLocation>
}