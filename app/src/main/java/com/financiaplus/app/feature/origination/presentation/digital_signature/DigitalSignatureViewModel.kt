package com.financiaplus.app.feature.origination.presentation.digital_signature

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
class DigitalSignatureViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DigitalSignatureUiState())
    val uiState: StateFlow<DigitalSignatureUiState> = _uiState.asStateFlow()

    init {
        loadSaved()
    }

    private fun loadSaved() {
        val signed = sessionManager.getDraftField(SessionManager.KEY_SIGNATURE) == "true"
        _uiState.update {
            it.copy(
                hasSigned = signed,
                acceptedTerms = signed
            )
        }
    }

    fun onSign() {
        _uiState.update { it.copy(hasSigned = true) }
    }

    fun onClearSignature() {
        _uiState.update { it.copy(hasSigned = false) }
    }

    fun onAcceptTermsChange(accepted: Boolean) {
        _uiState.update { it.copy(acceptedTerms = accepted) }
    }

    fun onContinue() {
        if (_uiState.value.canContinue) {
            sessionManager.saveDraftField(SessionManager.KEY_SIGNATURE, "true")
            sessionManager.saveCurrentStep(StepData.DIGITAL_SIGNATURE.current)
            _uiState.update { it.copy(navigateToNext = true) }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}