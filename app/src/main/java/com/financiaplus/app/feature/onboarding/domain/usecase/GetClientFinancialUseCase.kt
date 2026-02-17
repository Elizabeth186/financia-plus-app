package com.financiaplus.app.feature.onboarding.domain.usecase

import com.financiaplus.app.feature.onboarding.domain.model.ClientFinancial
import com.financiaplus.app.feature.onboarding.domain.repository.BankRepository
import javax.inject.Inject

class GetClientFinancialUseCase @Inject constructor(
    private val repository: BankRepository
) {
    suspend operator fun invoke(documentId: String): Result<ClientFinancial> {
        return repository.getClientFinancial(documentId)
    }
}