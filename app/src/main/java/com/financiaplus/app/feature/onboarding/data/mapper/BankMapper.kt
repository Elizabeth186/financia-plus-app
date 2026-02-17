package com.financiaplus.app.feature.onboarding.data.mapper

import com.financiaplus.app.core.network.dto.ClientFinancialResponse
import com.financiaplus.app.core.network.dto.ClientProfileResponse
import com.financiaplus.app.feature.onboarding.domain.model.ClientFinancial
import com.financiaplus.app.feature.onboarding.domain.model.ClientProfile

fun ClientProfileResponse.toClientProfile(): ClientProfile {
    return ClientProfile(
        found = found,
        fullName = client?.fullName,
        documentId = client?.documentId,
        email = client?.email,
        phone = client?.phone,
        address = client?.address,
        message = message
    )
}

fun ClientFinancialResponse.toClientFinancial(): ClientFinancial {
    return ClientFinancial(
        found = found,
        creditScore = financial?.creditScore,
        canContinue = canContinue,
        message = message
    )
}