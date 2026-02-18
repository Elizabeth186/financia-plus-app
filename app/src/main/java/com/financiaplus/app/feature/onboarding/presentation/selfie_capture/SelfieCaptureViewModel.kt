package com.financiaplus.app.feature.onboarding.presentation.selfie_capture

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
class SelfieCaptureViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val selfieImages = listOf(
        SelfieImage(
            drawableRes = R.drawable.left,
            qualityPercent = 40,
            qualityLabel = "Rostro no centrado",
            isAcceptable = false
        ),
        SelfieImage(
            drawableRes = R.drawable.front,
            qualityPercent = 95,
            qualityLabel = "Rostro centrado",
            isAcceptable = true
        )
    )

    private var currentIndex = 0

    private val _uiState = MutableStateFlow(SelfieCaptureUiState())
    val uiState: StateFlow<SelfieCaptureUiState> = _uiState.asStateFlow()

    init {
        loadSavedSelfie()
    }

    private fun loadSavedSelfie() {
        val savedRes = sessionManager.getDraftField(SessionManager.KEY_SELFIE_IMAGE)
        if (savedRes != null) {
            val image = selfieImages.find { it.drawableRes.toString() == savedRes }
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
        val image = selfieImages[currentIndex]
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
                it.copy(qualityError = R.string.selfie_quality_error)
            }
            return
        }
        sessionManager.saveDraftField(
            SessionManager.KEY_SELFIE_IMAGE,
            image.drawableRes.toString()
        )
        _uiState.update { it.copy(isAccepted = true, qualityError = null) }
    }

    fun onRetry() {
        if (currentIndex < selfieImages.size - 1) currentIndex++
        _uiState.update {
            it.copy(
                currentImage = selfieImages[currentIndex],
                isCaptured = true,
                qualityError = null,
                isAccepted = false
            )
        }
    }

    fun onContinue() {
        sessionManager.saveCurrentStep(StepData.SELFIE_CAPTURE.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}