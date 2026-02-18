package com.financiaplus.app.feature.origination.presentation.digital_signature

data class DigitalSignatureUiState(
    val hasSigned: Boolean = false,
    val acceptedTerms: Boolean = false,
    val isLoading: Boolean = false,
    val navigateToNext: Boolean = false
) {
    val canContinue: Boolean
        get() = hasSigned && acceptedTerms
}