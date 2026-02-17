package com.financiaplus.app.feature.onboarding.domain.repository

import com.financiaplus.app.feature.onboarding.domain.model.ClientFinancial
import com.financiaplus.app.feature.onboarding.domain.model.ClientProfile

interface BankRepository {
    suspend fun getClientProfile(documentId: String): Result<ClientProfile>
    suspend fun getClientFinancial(documentId: String): Result<ClientFinancial>
}