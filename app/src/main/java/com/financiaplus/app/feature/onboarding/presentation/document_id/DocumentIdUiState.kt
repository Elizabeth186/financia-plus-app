package com.financiaplus.app.feature.onboarding.presentation.document_id

data class DocumentIdUiState(
    val documentId: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigateToNext: Boolean = false
)