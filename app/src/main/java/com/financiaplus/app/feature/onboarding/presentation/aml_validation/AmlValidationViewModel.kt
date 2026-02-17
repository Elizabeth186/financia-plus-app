package com.financiaplus.app.feature.onboarding.presentation.aml_validation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financiaplus.app.core.security.SessionManager
import com.financiaplus.app.feature.onboarding.domain.usecase.CheckAmlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AmlValidationViewModel @Inject constructor(
    private val checkAmlUseCase: CheckAmlUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(AmlValidationUiState())
    val uiState: StateFlow<AmlValidationUiState> = _uiState.asStateFlow()

    init {
        validateAml()
    }

    private fun validateAml() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val documentId = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_ID)
            val fullName = sessionManager.getDraftField(SessionManager.KEY_FULL_NAME)

            val result = if (documentId != null) {
                checkAmlUseCase(documentId = documentId)
            } else if (fullName != null) {
                checkAmlUseCase(name = fullName)
            } else {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se encontraron datos del usuario")
                }
                return@launch
            }

            result.fold(
                onSuccess = { amlResult ->
                    if (amlResult.found) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                found = true,
                                isBlocked = true,
                                personName = amlResult.fullName,
                                reason = amlResult.reason
                            )
                        }
                    } else {
                        delay(1500)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                found = false,
                                navigateToNext = true
                            )
                        }
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al validar AML"
                        )
                    }
                }
            )
        }
    }

    fun onRetry() {
        _uiState.update { AmlValidationUiState() }
        validateAml()
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}