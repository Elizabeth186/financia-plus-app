package com.financiaplus.app.feature.onboarding.presentation.geo_location

import com.financiaplus.app.feature.onboarding.domain.model.GeoLocation


data class GeoLocationUiState(
    val isLoading: Boolean = false,
    val geoLocation: GeoLocation? = null,
    val error: String? = null,
    val navigateToNext: Boolean = false
)