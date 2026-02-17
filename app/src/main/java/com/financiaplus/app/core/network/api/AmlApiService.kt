package com.financiaplus.app.core.network.api

import com.financiaplus.app.core.network.NetworkConstants
import com.financiaplus.app.core.network.dto.AmlResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface AmlApiService {

    @GET("api/v1/aml")
    suspend fun checkAml(
        @Header(NetworkConstants.HEADER_API_KEY) apiKey: String,
        @Query("name") name: String? = null,
        @Query("documentId") documentId: String? = null
    ): Response<AmlResponse>
}
