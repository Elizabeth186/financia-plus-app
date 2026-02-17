package com.financiaplus.app.feature.onboarding.domain.usecase

import com.financiaplus.app.feature.onboarding.domain.model.ClientProfile
import com.financiaplus.app.feature.onboarding.domain.repository.BankRepository
import javax.inject.Inject

class GetClientProfileUseCase @Inject constructor(
    private val repository: BankRepository
) {
    suspend operator fun invoke(documentId: String): Result<ClientProfile> {
        return repository.getClientProfile(documentId)
    }
}