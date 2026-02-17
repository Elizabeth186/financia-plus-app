package com.financiaplus.app.feature.onboarding.presentation.document_review

data class DocumentReviewUiState(
    val documentId: String = "",
    val fullName: String = "",
    val birthDate: String = "",
    val documentImageRes: String? = null,
    val navigateToNext: Boolean = false
)
