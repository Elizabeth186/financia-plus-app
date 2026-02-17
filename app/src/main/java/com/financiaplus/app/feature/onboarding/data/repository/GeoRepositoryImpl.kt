package com.financiaplus.app.feature.onboarding.data.repository

import com.financiaplus.app.core.network.api.GeoLocationApiService
import com.financiaplus.app.feature.onboarding.data.mapper.toGeoLocation
import com.financiaplus.app.feature.onboarding.domain.model.GeoLocation
import com.financiaplus.app.feature.onboarding.domain.repository.GeoRepository
import javax.inject.Inject

class GeoRepositoryImpl @Inject constructor(
    private val apiService: GeoLocationApiService
) : GeoRepository {

    override suspend fun getGeoLocation(): Result<GeoLocation> {
        return try {
            val response = apiService.getGeoLocation()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body.toGeoLocation())
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