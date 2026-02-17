package com.financiaplus.app.feature.onboarding.domain.usecase

import com.financiaplus.app.feature.onboarding.domain.model.AmlResult
import com.financiaplus.app.feature.onboarding.domain.repository.AmlRepository
import javax.inject.Inject

class CheckAmlUseCase @Inject constructor(
    private val repository: AmlRepository
) {
    suspend operator fun invoke(
        name: String? = null,
        documentId: String? = null
    ): Result<AmlResult> {
        return if (documentId != null) {
            repository.checkByDocumentId(documentId)
        } else if (name != null) {
            repository.checkByName(name)
        } else {
            Result.failure(Exception("You need name or documentId"))
        }
    }
}