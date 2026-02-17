package com.financiaplus.app.core.navigation

import com.financiaplus.app.R

data class StepInfo(
    val title: Int,
    val current: Int,
    val total: Int
)

object StepData {
    private const val TOTAL = 16

    val DOCUMENT_ID = StepInfo(R.string.document_id, 1, TOTAL)
    val AML_VALIDATION    = StepInfo(R.string.aml_validation,2,  TOTAL)
    val CLIENT_CHECK      = StepInfo(R.string.client_check,3,  TOTAL)


}