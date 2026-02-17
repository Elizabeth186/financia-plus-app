package com.financiaplus.app.core.network.api

import com.financiaplus.app.core.network.NetworkConstants
import com.financiaplus.app.core.network.dto.ClientFinancialResponse
import com.financiaplus.app.core.network.dto.ClientProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface BankApiService {

    @GET("api/v1/clients/{documentId}/profile")
    suspend fun getClientProfile(
        @Header(NetworkConstants.HEADER_API_KEY) apiKey: String,
        @Path("documentId") documentId: String
    ): Response<ClientProfileResponse>

    @GET("api/v1/clients/{documentId}/financial")
    suspend fun getClientFinancial(
        @Header(NetworkConstants.HEADER_API_KEY) apiKey: String,
        @Path("documentId") documentId: String
    ): Response<ClientFinancialResponse>
}