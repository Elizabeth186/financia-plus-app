package com.financiaplus.app.feature.origination.presentation.economic_profile

data class EconomicProfileUiState(
    val monthlyIncome: String = "",
    val occupation: String = "",
    val dependents: String = "",
    val isLoading: Boolean = false,
    val navigateToNext: Boolean = false
) {
    val isFormValid: Boolean
        get() = monthlyIncome.isNotBlank()
                && occupation.isNotBlank()
                && dependents.isNotBlank()
}