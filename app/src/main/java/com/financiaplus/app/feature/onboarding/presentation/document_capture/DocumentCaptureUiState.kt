package com.financiaplus.app.feature.onboarding.presentation.document_capture

data class DocumentImage(
    val drawableRes: Int,
    val qualityPercent: Int,
    val qualityLabel: String,
    val isAcceptable: Boolean
)

data class DocumentCaptureUiState(
    val currentImage: DocumentImage? = null,
    val isCaptured: Boolean = false,
    val isAccepted: Boolean = false,
    val qualityError: Int? = null,
    val navigateToNext: Boolean = false
)