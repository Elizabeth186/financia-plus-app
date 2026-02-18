package com.financiaplus.app.feature.origination.presentation.summary

data class SummaryUiState(
    val fullName: String = "",
    val documentId: String = "",
    val email: String = "",
    val phone: String = "",
    val monthlyIncome: String = "",
    val occupation: String = "",
    val cardType: String = "",
    val creditLimit: String = "",
    val isLoading: Boolean = false,
    val navigateToNext: Boolean = false
)