package com.financiaplus.app.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    }
}

fun SaveStartRoute(savedStep: Int): String {
    return when (savedStep) {
        StepData.DOCUMENT_ID.current -> Screen.DocumentId.route
        else -> Screen.DocumentId.route
    }
}