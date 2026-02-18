package com.financiaplus.app.feature.origination.presentation.card_selection

data class CardSelectionUiState(
    val cardType: String = "",
    val creditLimit: String = "",
    val isLoading: Boolean = false,
    val navigateToNext: Boolean = false
) {
    val isFormValid: Boolean
        get() = cardType.isNotBlank() && creditLimit.isNotBlank()
}