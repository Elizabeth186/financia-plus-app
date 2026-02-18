package com.financiaplus.app.feature.onboarding.presentation.personal_data

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
class PersonalDataViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(PersonalDataUiState())
    val uiState: StateFlow<PersonalDataUiState> = _uiState.asStateFlow()

    init {
        loadDraft()
    }

    private fun loadDraft() {
        _uiState.update { state ->
            state.copy(
                documentId = sessionManager.getDraftField(SessionManager.KEY_DOCUMENT_ID) ?: "",
                fullName = sessionManager.getDraftField(SessionManager.KEY_FULL_NAME) ?: "",
                birthDate = sessionManager.getDraftField(SessionManager.KEY_BIRTH_DATE) ?: "",
                email = sessionManager.getDraftField(SessionManager.KEY_EMAIL) ?: "",
                phone = sessionManager.getDraftField(SessionManager.KEY_PHONE) ?: "",
                address = sessionManager.getDraftField(SessionManager.KEY_ADDRESS) ?: "",
                gender = sessionManager.getDraftField(SessionManager.KEY_GENDER) ?: ""
            )
        }
    }

    fun onEmailChange(value: String) {
        _uiState.update { it.copy(email = value) }
        sessionManager.saveDraftField(SessionManager.KEY_EMAIL, value)
    }

    fun onPhoneChange(value: String) {
        _uiState.update { it.copy(phone = value) }
        sessionManager.saveDraftField(SessionManager.KEY_PHONE, value)
    }

    fun onAddressChange(value: String) {
        _uiState.update { it.copy(address = value) }
        sessionManager.saveDraftField(SessionManager.KEY_ADDRESS, value)
    }

    fun onGenderChange(value: String) {
        _uiState.update { it.copy(gender = value) }
        sessionManager.saveDraftField(SessionManager.KEY_GENDER, value)
    }

    fun onContinue() {
        if (_uiState.value.isFormValid) {
            sessionManager.saveCurrentStep(StepData.PERSONAL_DATA.current)
            _uiState.update { it.copy(navigateToNext = true) }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}