package com.financiaplus.app.feature.origination.presentation.address_proof

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
class AddressProofViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddressProofUiState())
    val uiState: StateFlow<AddressProofUiState> = _uiState.asStateFlow()

    init {
        loadSaved()
    }

    private fun loadSaved() {
        val saved = sessionManager.getDraftField(SessionManager.KEY_ADDRESS_PROOF)
        if (saved != null) {
            _uiState.update {
                it.copy(
                    hasDocument = true,
                    documentName = saved
                )
            }
        }
    }

    fun onUploadDocument() {
        val mockFileName = "comprobante_domicilio.pdf"
        sessionManager.saveDraftField(SessionManager.KEY_ADDRESS_PROOF, mockFileName)
        _uiState.update {
            it.copy(
                hasDocument = true,
                documentName = mockFileName
            )
        }
    }

    fun onRemoveDocument() {
        sessionManager.saveDraftField(SessionManager.KEY_ADDRESS_PROOF, "")
        _uiState.update {
            it.copy(
                hasDocument = false,
                documentName = null
            )
        }
    }

    fun onContinue() {
        sessionManager.saveCurrentStep(StepData.ADDRESS_PROOF.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}