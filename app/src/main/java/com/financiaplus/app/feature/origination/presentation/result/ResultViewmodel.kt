package com.financiaplus.app.feature.origination.presentation.result

import androidx.lifecycle.ViewModel
import com.financiaplus.app.R
import com.financiaplus.app.core.security.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResultUiState())
    val uiState: StateFlow<ResultUiState> = _uiState.asStateFlow()

    init {
        evaluateApplication()
    }

    private fun evaluateApplication() {
        val monthlyIncome = sessionManager.getDraftField(SessionManager.KEY_MONTHLY_INCOME)
            ?.toDoubleOrNull() ?: 0.0

        val status = when {
            monthlyIncome >= 1500 -> ApplicationStatus.APPROVED
            monthlyIncome >= 1000 -> ApplicationStatus.PENDING
            else -> ApplicationStatus.REJECTED
        }

        val messageRes = when (status) {
            ApplicationStatus.APPROVED -> R.string.result_approved_detail
            ApplicationStatus.PENDING  -> R.string.result_pending_detail
            ApplicationStatus.REJECTED -> R.string.result_rejected_detail
        }

        _uiState.value = ResultUiState(
            status = status,
            messageRes = messageRes
        )
    }

    fun onFinish() {
        sessionManager.clearDraft()
        sessionManager.saveCurrentStep(0)
    }
}