package com.financiaplus.app.feature.onboarding.presentation.geo_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.security.SessionManager
import com.financiaplus.app.feature.onboarding.domain.usecase.GetGeoLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeoLocationViewModel @Inject constructor(
    private val getGeoLocationUseCase: GetGeoLocationUseCase,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(GeoLocationUiState())
    val uiState: StateFlow<GeoLocationUiState> = _uiState.asStateFlow()

    init {
        fetchGeoLocation()
    }

    private fun fetchGeoLocation() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            getGeoLocationUseCase().fold(
                onSuccess = { geo ->
                    sessionManager.saveDraftField(SessionManager.KEY_GEO_IP, geo.ip ?: "")
                    sessionManager.saveDraftField(SessionManager.KEY_GEO_CITY, geo.city ?: "")
                    sessionManager.saveDraftField(SessionManager.KEY_GEO_COUNTRY, geo.countryName ?: "")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            geoLocation = geo
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al obtener ubicación"
                        )
                    }
                }
            )
        }
    }

    fun onRetry() {
        _uiState.update { GeoLocationUiState() }
        fetchGeoLocation()
    }

    fun onContinue() {
        sessionManager.saveCurrentStep(StepData.GEO_LOCATION.current)
        _uiState.update { it.copy(navigateToNext = true) }
    }

    fun onNavigated() {
        _uiState.update { it.copy(navigateToNext = false) }
    }
}