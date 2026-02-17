package com.financiaplus.app.feature.onboarding.data.repository

import com.financiaplus.app.core.network.NetworkConstants
import com.financiaplus.app.core.network.api.AmlApiService
import com.financiaplus.app.feature.onboarding.data.mapper.toAmlResult
import com.financiaplus.app.feature.onboarding.domain.model.AmlResult
import com.financiaplus.app.feature.onboarding.domain.repository.AmlRepository
import javax.inject.Inject


class AmlRepositoryImpl @Inject constructor(
    private val apiService: AmlApiService
) : AmlRepository {

    override suspend fun checkByName(name: String): Result<AmlResult> {
        return try {
            val response = apiService.checkAml(
                apiKey = NetworkConstants.AML_API_KEY,
                name = name
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toAmlResult())
                } else {
                    Result.failure(Exception("Data is empty"))
                }
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun checkByDocumentId(documentId: String): Result<AmlResult> {
        return try {
            val response = apiService.checkAml(
                apiKey = NetworkConstants.AML_API_KEY,
                documentId = documentId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toAmlResult())
                } else {
                    Result.failure(Exception("Data is empty"))
                }
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}