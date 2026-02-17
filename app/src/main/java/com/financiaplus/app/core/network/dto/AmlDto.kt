package com.financiaplus.app.core.network.dto

data class AmlResponse(
    val found: Boolean,
    val person: AmlPersonDto?,
    val message: String
)

data class AmlPersonDto(
    val fullName: String,
    val documentId: String,
    val birthDate: String,
    val nationality: String,
    val reason: String
)