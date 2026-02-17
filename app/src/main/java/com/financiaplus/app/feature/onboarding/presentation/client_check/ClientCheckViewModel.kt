package com.financiaplus.app.feature.onboarding.presentation.client_check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financiaplus.app.core.security.SessionManager
import com.financiaplus.app.feature.onboarding.domain.usecase.GetClientFinancialUseCase
import com.financiaplus.app.feature.onboarding.domain.usecase.GetClientProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class ClientCheckViewModel @Inject constructor(
    private val getClientProfileUseCase: GetClientProfileUseCase,
    private val getClientFinancialUseCase: GetClientFinancialUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientCheckUiState())
    val uiState: StateFlow<ClientCheckUiState> = _uiState.asStateFlow()

    init {
        checkClient()
    }

    private fun checkClient() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val documentId = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_ID)

            if (documentId == null) {
                _uiState.update {
                    it.copy(isLoading = false, error = "No se encontró número de documento")
                }
                return@launch
            }

            val profileResult = getClientProfileUseCase(documentId)

            profileResult.fold(
                onSuccess = { profile ->
                    if (profile.found) {
                        sessionManager.saveDraftField(SessionManager.KEY_FULL_NAME, profile.fullName ?: "")
                        sessionManager.saveDraftField(SessionManager.KEY_EMAIL, profile.email ?: "")
                        sessionManager.saveDraftField(SessionManager.KEY_PHONE, profile.phone ?: "")
                        sessionManager.saveDraftField(SessionManager.KEY_ADDRESS, profile.address ?: "")

                        val financialResult = getClientFinancialUseCase(documentId)
                        financialResult.fold(
                            onSuccess = { financial ->
                               delay(1500)
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        isExistingClient = true,
                                        fullName = profile.fullName,
                                        email = profile.email,
                                        phone = profile.phone,
                                        address = profile.address,
                                        creditScore = financial.creditScore,
                                        canContinue = financial.canContinue,
                                        isBlocked = !financial.canContinue,
                                        navigateToNext = financial.canContinue
                                    )
                                }
                            },
                            onFailure = { error ->
                                _uiState.update {
                                    it.copy(isLoading = false, error = error.message)
                                }
                            }
                        )
                    } else {

                        delay(1500)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                isExistingClient = false,
                                navigateToNext = true
                            )
                        }
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, error = error.message)
                    }
                }
            )
        }
    }

    fun onRetry() {
        _uiState.update { ClientCheckUiState() }
        checkClient()
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}