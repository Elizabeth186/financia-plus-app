package com.financiaplus.app.feature.origination.presentation.funds_declaration

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
class FundsDeclarationViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(FundsDeclarationUiState())
    val uiState: StateFlow<FundsDeclarationUiState> = _uiState.asStateFlow()

    init {
        loadDraft()
    }

    private fun loadDraft() {
        _uiState.update {
            it.copy(
                fundsOrigin = sessionManager.getDraftField(SessionManager.KEY_FUNDS_ORIGIN) ?: "",
                additionalInfo = sessionManager.getDraftField(SessionManager.KEY_FUNDS_INFO) ?: ""
            )
        }
    }

    fun onFundsOriginChange(value: String) {
        _uiState.update { it.copy(fundsOrigin = value) }
        sessionManager.saveDraftField(SessionManager.KEY_FUNDS_ORIGIN, value)
    }

    fun onAdditionalInfoChange(value: String) {
        _uiState.update { it.copy(additionalInfo = value) }
        sessionManager.saveDraftField(SessionManager.KEY_FUNDS_INFO, value)
    }

    fun onContinue() {
        if (_uiState.value.isFormValid) {
            sessionManager.saveCurrentStep(StepData.FUNDS_DECLARATION.current)
            _uiState.update { it.copy(navigateToNext = true) }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}