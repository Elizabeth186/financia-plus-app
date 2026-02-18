package com.financiaplus.app.feature.origination.presentation.funds_declaration

data class FundsDeclarationUiState(
    val fundsOrigin: String = "",
    val additionalInfo: String = "",
    val isLoading: Boolean = false,
    val navigateToNext: Boolean = false
) {
    val isFormValid: Boolean
        get() = fundsOrigin.isNotBlank()
}