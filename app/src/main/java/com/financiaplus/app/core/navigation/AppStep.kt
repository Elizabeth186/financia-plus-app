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
    val DOCUMENT_CAPTURE  = StepInfo(R.string.document_capture,4,  TOTAL)
    val DOCUMENT_REVIEW   = StepInfo(R.string.document_review,5,  TOTAL)
    val PERSONAL_DATA     = StepInfo(R.string.personal_data,6,  TOTAL)
    val SELFIE_CAPTURE    = StepInfo(R.string.selfie_capture,7,  TOTAL)
    val BIOMETRIC         = StepInfo(R.string.biometric_validation,8,  TOTAL)
    val GEO_LOCATION      = StepInfo(R.string.geo_location,9,  TOTAL)
    val ECONOMIC_PROFILE  = StepInfo(R.string.economic_profile,10,  TOTAL)
    val FUNDS_DECLARATION = StepInfo(R.string.funds_declaration,11,  TOTAL)
    val ADDRESS_PROOF     = StepInfo(R.string.address_proof,12,  TOTAL)
    val CARD_SELECTION    = StepInfo(R.string.card_selection,13,  TOTAL)
    val DIGITAL_SIGNATURE = StepInfo(R.string.digital_signature,14,  TOTAL)
    val SUMMARY           = StepInfo(R.string.summary,15,  TOTAL)
    val RESULT            = StepInfo(R.string.result,16,  TOTAL)














}