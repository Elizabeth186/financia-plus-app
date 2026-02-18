package com.financiaplus.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.financiaplus.app.feature.onboarding.presentation.aml_validation.AmlValidationRoot
import com.financiaplus.app.feature.onboarding.presentation.client_check.ClientCheckRoot
import com.financiaplus.app.feature.onboarding.presentation.document_capture.DocumentCaptureRoot
import com.financiaplus.app.feature.onboarding.presentation.document_id.DocumentIdRoot
import com.financiaplus.app.feature.onboarding.presentation.document_review.DocumentReviewRoot
import com.financiaplus.app.feature.onboarding.presentation.personal_data.PersonalDataRoot
import com.financiaplus.app.feature.onboarding.presentation.selfie_capture.SelfieCaptureRoot


@Composable
fun NavGraph(navController: NavHostController,   startRoute: String = Screen.DocumentId.route) {

    NavHost(
        navController = navController,
        startDestination = startRoute
    ) {
        // Onboarding
        composable(Screen.DocumentId.route) {
            DocumentIdRoot(navController = navController)
        }
        composable(Screen.AmlValidation.route) {
            AmlValidationRoot(navController = navController)
        }
        composable(Screen.ClientCheck.route) {
            ClientCheckRoot(navController = navController)
        }
        composable(Screen.DocumentCapture.route) {
            DocumentCaptureRoot(navController = navController)
        }
        composable(Screen.DocumentReview.route) {
            DocumentReviewRoot(navController = navController)
        }
        composable(Screen.PersonalData.route) {
            PersonalDataRoot(navController = navController)
        }
        composable(Screen.SelfieCapture.route) {
            SelfieCaptureRoot(navController = navController)
        }







    }
}

fun SaveStartRoute(savedStep: Int): String {
    return when (savedStep) {
        StepData.DOCUMENT_ID.current -> Screen.DocumentId.route
        StepData.AML_VALIDATION.current -> Screen.AmlValidation.route
        StepData.CLIENT_CHECK.current -> Screen.ClientCheck.route
        StepData.DOCUMENT_CAPTURE.current -> Screen.DocumentCapture.route
        StepData.DOCUMENT_REVIEW.current -> Screen.DocumentReview.route
        StepData.PERSONAL_DATA.current -> Screen.PersonalData.route




        else -> Screen.DocumentId.route
    }
}