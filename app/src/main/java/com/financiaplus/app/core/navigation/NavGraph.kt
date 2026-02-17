package com.financiaplus.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.financiaplus.app.feature.onboarding.presentation.aml_validation.AmlValidationRoot
import com.financiaplus.app.feature.onboarding.presentation.client_check.ClientCheckRoot
import com.financiaplus.app.feature.onboarding.presentation.document_id.DocumentIdRoot


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


    }
}

fun SaveStartRoute(savedStep: Int): String {
    return when (savedStep) {
        StepData.DOCUMENT_ID.current -> Screen.DocumentId.route
        StepData.AML_VALIDATION.current -> Screen.AmlValidation.route
        else -> Screen.DocumentId.route
    }
}