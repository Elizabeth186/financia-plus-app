package com.financiaplus.app.feature.onboarding.presentation.personal_data

data class PersonalDataUiState(
    val fullName: String = "",
    val documentId: String = "",
    val birthDate: String = "",
    val email: String = "",
    val phone: String = "",
    val address: String = "",
    val gender: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val navigateToNext: Boolean = false
) {
    val isFormValid: Boolean
        get() = fullName.isNotBlank()
                && birthDate.isNotBlank()
                && email.isNotBlank()
                && phone.isNotBlank()
                && address.isNotBlank()
                && gender.isNotBlank()
}