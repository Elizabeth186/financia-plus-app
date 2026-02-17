package com.financiaplus.app.core.network.dto

data class ClientProfileResponse(
    val found: Boolean,
    val client: ClientDto?,
    val message: String
)

data class ClientDto(
    val fullName: String,
    val documentId: String,
    val birthDate: String,
    val email: String,
    val phone: String,
    val address: String,
    val nationality: String,
    val gender: String
)