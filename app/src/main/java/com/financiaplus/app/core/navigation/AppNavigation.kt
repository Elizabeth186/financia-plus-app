package com.financiaplus.app.core.navigation


sealed class Screen(val route: String) {

    // Onboarding
    object DocumentId : Screen("onboarding/document_id")

}
