package com.financiaplus.app.feature.origination.presentation.summary

import androidx.lifecycle.ViewModel
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.security.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(SummaryUiState())
    val uiState: StateFlow<SummaryUiState> = _uiState.asStateFlow()

    init {
        loadAllData()
    }

    private fun loadAllData() {
        _uiState.update {
            it.copy(
                fullName = sessionManager.getDraftField(SessionManager.KEY_FULL_NAME) ?: "",
                documentId = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_ID) ?: "",
                email = sessionManager.getDraftField(SessionManager.KEY_EMAIL) ?: "",
                phone = sessionManager.getDraftField(SessionManager.KEY_PHONE) ?: "",
                monthlyIncome = sessionManager.getDraftField(SessionManager.KEY_MONTHLY_INCOME) ?: "",
                occupation = sessionManager.getDraftField(SessionManager.KEY_OCCUPATION) ?: "",
                cardType = sessionManager.getDraftField(SessionManager.KEY_CARD_TYPE) ?: "",
                creditLimit = sessionManager.getDraftField(SessionManager.KEY_CREDIT_LIMIT) ?: ""
            )
        }
    }

    fun onConfirm() {
        sessionManager.saveCurrentStep(StepData.SUMMARY.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}