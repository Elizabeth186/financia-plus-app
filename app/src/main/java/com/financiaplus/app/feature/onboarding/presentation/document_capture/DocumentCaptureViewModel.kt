package com.financiaplus.app.feature.onboarding.presentation.document_capture

import androidx.lifecycle.ViewModel
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.security.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DocumentCaptureViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val documentImages = listOf(
        DocumentImage(
            drawableRes = R.drawable.doc_low,
            qualityPercent = 40,
            qualityLabel = "Baja",
            isAcceptable = false
        ),
        DocumentImage(
            drawableRes = R.drawable.doc_medium,
            qualityPercent = 76,
            qualityLabel = "Media",
            isAcceptable = false
        ),
        DocumentImage(
            drawableRes = R.drawable.doc_high,
            qualityPercent = 100,
            qualityLabel = "Alta",
            isAcceptable = true
        )
    )

    private var currentIndex = 0

    private val _uiState = MutableStateFlow(DocumentCaptureUiState())
    val uiState: StateFlow<DocumentCaptureUiState> = _uiState.asStateFlow()

    init {
        loadSavedImage()
    }

    private fun loadSavedImage() {
        val savedRes = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_IMAGE)
        if (savedRes != null) {
            val image = documentImages.find {
                it.drawableRes.toString() == savedRes
            }
            if (image != null) {
                _uiState.update {
                    it.copy(
                        currentImage = image,
                        isCaptured = true,
                        isAccepted = true
                    )
                }
            }
        }
    }

    fun onCapture() {
        val image = documentImages[currentIndex]
        _uiState.update {
            it.copy(
                currentImage = image,
                isCaptured = true,
                qualityError = null
            )
        }
    }

    fun onAccept() {
        val image = _uiState.value.currentImage ?: return
        if (!image.isAcceptable) {
            _uiState.update {
                it.copy(qualityError = R.string.doc_capture_quality_error)
            }
            return
        }
        sessionManager.saveDraftField(
            SessionManager.KEY_DOCUMENT_IMAGE,
            image.drawableRes.toString()
        )

        val existingName = sessionManager.getDraftField(SessionManager.KEY_FULL_NAME)
        val existingBirthDate = sessionManager.getDraftField(SessionManager.KEY_BIRTH_DATE)

        if (existingName.isNullOrBlank()) {
            sessionManager.saveDraftField(SessionManager.KEY_FULL_NAME, "Ana María Pérez")
        }
        if (existingBirthDate.isNullOrBlank()) {
            sessionManager.saveDraftField(SessionManager.KEY_BIRTH_DATE, "1990-05-15")
        }

        _uiState.update { it.copy(isAccepted = true, qualityError = null) }
    }

    fun onRetry() {
        if (currentIndex < documentImages.size - 1) currentIndex++
        _uiState.update {
            it.copy(
                currentImage = documentImages[currentIndex],
                isCaptured = true,
                qualityError = null,
                isAccepted = false
            )
        }
    }

    fun onContinue() {
        sessionManager.saveCurrentStep(StepData.DOCUMENT_CAPTURE.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}