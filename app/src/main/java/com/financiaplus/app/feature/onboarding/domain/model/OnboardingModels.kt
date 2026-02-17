package com.financiaplus.app.feature.onboarding.domain.model

data class PersonalData(
    val fullName: String,
    val documentId: String,
    val birthDate: String,
    val email: String,
    val phone: String,
    val address: String,
    val gender: String,
    val nationality: String = "SV"
)

data class AmlResult(
    val found: Boolean,
    val fullName: String? = null,
    val documentId: String? = null,
    val reason: String? = null,
    val message: String
)

data class ClientProfile(
    val found: Boolean,
    val fullName: String? = null,
    val documentId: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val message: String
)

data class ClientFinancial(
    val found: Boolean,
    val creditScore: Double? = null,
    val canContinue: Boolean,
    val message: String
)

data class GeoLocation(
    val ip: String? = null,
    val city: String? = null,
    val region: String? = null,
    val country: String? = null,
    val countryName: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
)