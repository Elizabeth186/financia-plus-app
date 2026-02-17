package com.financiaplus.app.feature.onboarding.data.mapper

import com.financiaplus.app.core.network.dto.AmlResponse
import com.financiaplus.app.feature.onboarding.domain.model.AmlResult

fun AmlResponse.toAmlResult(): AmlResult {
    return AmlResult(
        found = found,
        fullName = person?.fullName,
        documentId = person?.documentId,
        reason = person?.reason,
        message = message
    )
}