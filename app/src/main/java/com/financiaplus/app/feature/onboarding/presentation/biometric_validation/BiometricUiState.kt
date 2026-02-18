package com.financiaplus.app.feature.onboarding.presentation.biometric_validation

data class BiometricValidationUiState(
    val isLoading: Boolean = false,
    val matchPercent: Int? = null,
    val isApproved: Boolean = false,
    val navigateToNext: Boolean = false
)