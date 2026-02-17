package com.financiaplus.app.core.navigation


sealed class Screen(val route: String) {

    // Onboarding
    object DocumentId : Screen("onboarding/document_id")
    object AmlValidation : Screen("onboarding/aml_validation")
    object ClientCheck : Screen("onboarding/client_check")
    object DocumentCapture : Screen("onboarding/document_capture")





}
