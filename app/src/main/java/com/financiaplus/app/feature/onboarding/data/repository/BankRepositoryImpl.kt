package com.financiaplus.app.feature.onboarding.data.repository

import com.financiaplus.app.core.network.NetworkConstants
import com.financiaplus.app.core.network.api.BankApiService
import com.financiaplus.app.feature.onboarding.data.mapper.toClientFinancial
import com.financiaplus.app.feature.onboarding.data.mapper.toClientProfile
import com.financiaplus.app.feature.onboarding.domain.model.ClientFinancial
import com.financiaplus.app.feature.onboarding.domain.model.ClientProfile
import com.financiaplus.app.feature.onboarding.domain.repository.BankRepository
import javax.inject.Inject

class BankRepositoryImpl @Inject constructor(
    private val apiService: BankApiService
) : BankRepository {

    override suspend fun getClientProfile(documentId: String): Result<ClientProfile> {
        return try {
            val response = apiService.getClientProfile(
                apiKey = NetworkConstants.BANK_API_KEY,
                documentId = documentId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toClientProfile())
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

    override suspend fun getClientFinancial(documentId: String): Result<ClientFinancial> {
        return try {
            val response = apiService.getClientFinancial(
                apiKey = NetworkConstants.BANK_API_KEY,
                documentId = documentId
            )
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toClientFinancial())
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