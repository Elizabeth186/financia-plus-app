package com.financiaplus.app.core.navigation


sealed class Screen(val route: String) {

    // Onboarding
    object DocumentId : Screen("onboarding/document_id")
    object AmlValidation : Screen("onboarding/aml_validation")
    object ClientCheck : Screen("onboarding/client_check")
    object DocumentCapture : Screen("onboarding/document_capture")
    object DocumentReview : Screen("onboarding/document_review")
    object PersonalData : Screen("onboarding/personal_data")
    object SelfieCapture : Screen("onboarding/selfie_capture")
    object BiometricValidation : Screen("onboarding/biometric_validation")
    object GeoLocation : Screen("onboarding/geo_location")

    // Origination
    object EconomicProfile : Screen("onboarding/economic_profile")













}
