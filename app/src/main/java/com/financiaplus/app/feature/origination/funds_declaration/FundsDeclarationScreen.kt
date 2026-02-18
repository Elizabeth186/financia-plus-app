package com.financiaplus.app.feature.origination.presentation.funds_declaration

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
fun FundsDeclarationRoot(
    navController: NavController,
    viewModel: FundsDeclarationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.AddressProof.route)
            viewModel.onNavigated()
        }
    }

    FundsDeclarationScreen(
        uiState = uiState,
        onFundsOriginChange = viewModel::onFundsOriginChange,
        onAdditionalInfoChange = viewModel::onAdditionalInfoChange,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun FundsDeclarationScreen(
    uiState: FundsDeclarationUiState,
    onFundsOriginChange: (String) -> Unit,
    onAdditionalInfoChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.FUNDS_DECLARATION)
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
                text = stringResource(R.string.funds_declaration_origin) ,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.funds_declaration_origin_detail) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            FinanciaDropdown(
                label = R.string.funds_declaration_principal_origin ,
                options = listOf(
                    "SALARY" to "Salario/Empleo",
                    "BUSINESS" to "Negocio propio",
                    "INVESTMENTS" to "Inversiones",
                    "RETIREMENT" to "Pensión/Jubilación",
                    "OTHER" to "Otro"
                ),
                selectedCode = uiState.fundsOrigin,
                onOptionSelected = onFundsOriginChange
            )

            FinancialTextField(
                value = uiState.additionalInfo,
                onValueChange = onAdditionalInfoChange,
                label = R.string.funds_declaration_additional_info
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
private fun FundsDeclarationPreview() {
    MaterialTheme {
        FundsDeclarationScreen(
            uiState = FundsDeclarationUiState(fundsOrigin = "SALARY"),
            onFundsOriginChange = {},
            onAdditionalInfoChange = {},
            onContinue = {}
        )
    }
}