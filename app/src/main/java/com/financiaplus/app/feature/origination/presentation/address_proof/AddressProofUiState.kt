package com.financiaplus.app.feature.origination.presentation.address_proof

data class AddressProofUiState(
    val hasDocument: Boolean = false,
    val documentName: String? = null,
    val isLoading: Boolean = false,
    val navigateToNext: Boolean = false
)