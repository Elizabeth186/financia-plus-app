package com.financiaplus.app.feature.onboarding.presentation.client_check

data class ClientCheckUiState(
    val isLoading: Boolean = false,
    val isExistingClient: Boolean = false,
    val fullName: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val creditScore: Double? = null,
    val canContinue: Boolean = true,
    val error: String? = null,
    val navigateToNext: Boolean = false,
    val isBlocked: Boolean = false
)