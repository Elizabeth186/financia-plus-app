package com.financiaplus.app.feature.onboarding.presentation.document_id

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
class DocumentIdViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DocumentIdUiState())
    val uiState: StateFlow<DocumentIdUiState> = _uiState.asStateFlow()

    init {
        loadSavedDocument()
    }

    private fun loadSavedDocument() {
        val savedDoc = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_ID)
        if (savedDoc != null) {
            _uiState.update { it.copy(documentId = savedDoc) }
        }
    }

    fun onDocumentIdChanged(value: String) {
        val previousDocumentId = _uiState.value.documentId

        _uiState.update { it.copy(documentId = value, error = null) }

        if (previousDocumentId.isNotBlank() && previousDocumentId != value) {
            sessionManager.clearDraft()
        }
        sessionManager.saveDraftField(SessionManager.KEY_DOCUMENT_ID, value)
    }

    fun onContinue() {
        val documentId = _uiState.value.documentId.trim()

        if (documentId.isBlank()) {
            _uiState.update { it.copy(error = "El documento es requerido") }
            return
        }
        sessionManager.saveDraftField(SessionManager.KEY_DOCUMENT_ID, documentId)
        sessionManager.saveCurrentStep(StepData.DOCUMENT_ID.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
          _uiState.update { it.copy(navigateToNext = false) }
    }
}