package com.financiaplus.app.feature.origination.presentation.economic_profile

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
class EconomicProfileViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(EconomicProfileUiState())
    val uiState: StateFlow<EconomicProfileUiState> = _uiState.asStateFlow()

    init {
        loadDraft()
    }

    private fun loadDraft() {
        _uiState.update {
            it.copy(
                monthlyIncome = sessionManager.getDraftField(SessionManager.KEY_MONTHLY_INCOME) ?: "",
                occupation = sessionManager.getDraftField(SessionManager.KEY_OCCUPATION) ?: "",
                dependents = sessionManager.getDraftField(SessionManager.KEY_DEPENDENTS) ?: ""
            )
        }
    }

    fun onMonthlyIncomeChange(value: String) {
        _uiState.update { it.copy(monthlyIncome = value) }
        sessionManager.saveDraftField(SessionManager.KEY_MONTHLY_INCOME, value)
    }

    fun onOccupationChange(value: String) {
        _uiState.update { it.copy(occupation = value) }
        sessionManager.saveDraftField(SessionManager.KEY_OCCUPATION, value)
    }

    fun onDependentsChange(value: String) {
        _uiState.update { it.copy(dependents = value) }
        sessionManager.saveDraftField(SessionManager.KEY_DEPENDENTS, value)
    }

    fun onContinue() {
        if (_uiState.value.isFormValid) {
            sessionManager.saveCurrentStep(StepData.ECONOMIC_PROFILE.current)
            _uiState.update { it.copy(navigateToNext = true) }
        }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}