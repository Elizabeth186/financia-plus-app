package com.financiaplus.app.feature.onboarding.presentation.aml_validation

data class AmlValidationUiState(
    val isLoading: Boolean = false,
    val found: Boolean = false,
    val reason: String? = null,
    val personName: String? = null,
    val error: String? = null,
    val navigateToNext: Boolean = false,
    val isBlocked: Boolean = false
)