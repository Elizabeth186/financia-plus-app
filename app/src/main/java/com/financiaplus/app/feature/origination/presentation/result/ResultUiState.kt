package com.financiaplus.app.feature.origination.presentation.result

import com.financiaplus.app.R

enum class ApplicationStatus {
    APPROVED, REJECTED, PENDING
}

data class ResultUiState(
    val status: ApplicationStatus = ApplicationStatus.APPROVED,
    val messageRes: Int? = null
)