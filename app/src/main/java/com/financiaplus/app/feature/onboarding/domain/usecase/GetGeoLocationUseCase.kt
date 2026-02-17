package com.financiaplus.app.feature.onboarding.domain.usecase

import com.financiaplus.app.feature.onboarding.domain.model.GeoLocation
import com.financiaplus.app.feature.onboarding.domain.repository.GeoRepository
import javax.inject.Inject

class GetGeoLocationUseCase @Inject constructor(
    private val repository: GeoRepository
) {
    suspend operator fun invoke(): Result<GeoLocation> {
        return repository.getGeoLocation()
    }
}