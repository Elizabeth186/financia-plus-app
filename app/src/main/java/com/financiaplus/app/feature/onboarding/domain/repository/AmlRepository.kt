package com.financiaplus.app.feature.onboarding.domain.repository


import com.financiaplus.app.feature.onboarding.domain.model.AmlResult

interface AmlRepository {
    suspend fun checkByName(name: String): Result<AmlResult>
    suspend fun checkByDocumentId(documentId: String): Result<AmlResult>
}