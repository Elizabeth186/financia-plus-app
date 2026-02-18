package com.financiaplus.app.feature.origination.presentation.card_selection

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
class CardSelectionViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(CardSelectionUiState())
    val uiState: StateFlow<CardSelectionUiState> = _uiState.asStateFlow()

    init {
        loadDraft()
    }

    private fun loadDraft() {
        _uiState.update {
            it.copy(
                cardType = sessionManager.getDraftField(SessionManager.KEY_CARD_TYPE) ?: "",
                creditLimit = sessionManager.getDraftField(SessionManager.KEY_CREDIT_LIMIT) ?: ""
            )
        }
    }

    fun onCardTypeChange(value: String) {
        _uiState.update { it.copy(cardType = value) }
        sessionManager.saveDraftField(SessionManager.KEY_CARD_TYPE, value)
    }

    fun onCreditLimitChange(value: String) {
        _uiState.update { it.copy(creditLimit = value) }
        sessionManager.saveDraftField(SessionManager.KEY_CREDIT_LIMIT, value)
    }

    fun onContinue() {
        if (_uiState.value.isFormValid) {
            sessionManager.saveCurrentStep(StepData.CARD_SELECTION.current)
            _uiState.update { it.copy(navigateToNext = true) }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}