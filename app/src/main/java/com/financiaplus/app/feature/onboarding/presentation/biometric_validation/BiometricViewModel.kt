package com.financiaplus.app.feature.onboarding.presentation.biometric_validation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financiaplus.app.core.security.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BiometricValidationViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(BiometricValidationUiState())
    val uiState: StateFlow<BiometricValidationUiState> = _uiState.asStateFlow()

    init {
        validate()
    }

    private fun validate() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(3000)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    matchPercent = 94,
                    isApproved = true
                )
            }
        }
    }

    fun onContinue() {
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}