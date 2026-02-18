package com.financiaplus.app.feature.onboarding.presentation.selfie_capture

data class SelfieImage(
    val drawableRes: Int,
    val qualityPercent: Int,
    val qualityLabel: String,
    val isAcceptable: Boolean
)

data class SelfieCaptureUiState(
    val currentImage: SelfieImage? = null,
    val isCaptured: Boolean = false,
    val isAccepted: Boolean = false,
    val qualityError: Int? = null,
    val navigateToNext: Boolean = false
)