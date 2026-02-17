package com.financiaplus.app.core.network.dto

data class ClientFinancialResponse(
    val found: Boolean,
    val financial: ClientFinancialDto?,
    val canContinue: Boolean,
    val message: String
)

data class ClientFinancialDto(
    val creditScore: Double,
    val monthlyIncome: Double,
    val accountStatus: String,
    val productCount: Int,
    val hasDebitCard: Boolean,
    val hasSavingsAccount: Boolean
)