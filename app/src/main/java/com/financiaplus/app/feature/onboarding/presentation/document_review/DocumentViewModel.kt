package com.financiaplus.app.feature.onboarding.presentation.document_review

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
class DocumentReviewViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(DocumentReviewUiState())
    val uiState: StateFlow<DocumentReviewUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _uiState.update {
            it.copy(
                documentId = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_ID) ?: "",
                fullName = sessionManager.getDraftField(SessionManager.KEY_FULL_NAME) ?: "",
                birthDate = sessionManager.getDraftField(SessionManager.KEY_BIRTH_DATE) ?: "",
                documentImageRes = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_IMAGE)
            )
        }
    }

    fun onConfirm() {
        sessionManager.saveCurrentStep(StepData.DOCUMENT_REVIEW.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}