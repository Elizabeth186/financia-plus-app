package com.financiaplus.app.feature.origination.presentation.economic_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.Screen
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.ui.FinanciaButton
import com.financiaplus.app.core.ui.FinanciaDropdown
import com.financiaplus.app.core.ui.FinancialTextField
import com.financiaplus.app.core.ui.FinancialTopBar

@Composable
fun EconomicProfileRoot(
    navController: NavController,
    viewModel: EconomicProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.FundsDeclaration.route)
            viewModel.onNavigated()
        }
    }

    EconomicProfileScreen(
        uiState = uiState,
        onMonthlyIncomeChange = viewModel::onMonthlyIncomeChange,
        onOccupationChange = viewModel::onOccupationChange,
        onDependentsChange = viewModel::onDependentsChange,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun EconomicProfileScreen(
    uiState: EconomicProfileUiState,
    onMonthlyIncomeChange: (String) -> Unit,
    onOccupationChange: (String) -> Unit,
    onDependentsChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.ECONOMIC_PROFILE)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.economic_pro_economic_situation),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.economic_pro_economic_situation_detail) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            FinancialTextField(
                value = uiState.monthlyIncome,
                onValueChange = onMonthlyIncomeChange,
                label = R.string.economic_pro_monthly_income ,
                keyboardType = KeyboardType.Number
            )

            FinancialTextField(
                value = uiState.occupation,
                onValueChange = onOccupationChange,
                label = R.string.economic_pro_occupation
            )

            FinanciaDropdown(
                label = R.string.economic_pro_dependents ,
                options = listOf(
                    "0" to "0 - Sin dependientes",
                    "1" to "1 dependiente",
                    "2" to "2 dependientes",
                    "3" to "3 dependientes",
                    "4" to "4 dependientes",
                    "5" to "5+ dependientes"
                ),
                selectedCode = uiState.dependents,
                onOptionSelected = onDependentsChange
            )

            Spacer(Modifier.height(16.dp))

            FinanciaButton(
                text = R.string.next ,
                onClick = onContinue,
                enabled = uiState.isFormValid,
                isLoading = uiState.isLoading
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EconomicProfilePreview() {
    MaterialTheme {
        EconomicProfileScreen(
            uiState = EconomicProfileUiState(
                monthlyIncome = "1500",
                occupation = "Desarrollador",
                dependents = "2"
            ),
            onMonthlyIncomeChange = {},
            onOccupationChange = {},
            onDependentsChange = {},
            onContinue = {}
        )
    }
}