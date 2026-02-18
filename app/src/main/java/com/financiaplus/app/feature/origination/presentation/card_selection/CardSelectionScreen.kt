package com.financiaplus.app.feature.origination.presentation.card_selection

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
import com.financiaplus.app.core.ui.FinancialTopBar

@Composable
fun CardSelectionRoot(
    navController: NavController,
    viewModel: CardSelectionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.DigitalSignature.route)
            viewModel.onNavigated()
        }
    }

    CardSelectionScreen(
        uiState = uiState,
        onCardTypeChange = viewModel::onCardTypeChange,
        onCreditLimitChange = viewModel::onCreditLimitChange,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun CardSelectionScreen(
    uiState: CardSelectionUiState,
    onCardTypeChange: (String) -> Unit,
    onCreditLimitChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.CARD_SELECTION)
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
                text = stringResource(R.string.card_selection_title) ,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.card_selection_subtitle) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            FinanciaDropdown(
                label = R.string.card_selection_card_type ,
                options = listOf(
                    "CLASSIC" to "Clásica - Beneficios básicos",
                    "GOLD" to "Gold - Beneficios adicionales",
                    "PLATINUM" to "Platinum - Beneficios premium"
                ),
                selectedCode = uiState.cardType,
                onOptionSelected = onCardTypeChange
            )

            FinanciaDropdown(
                label = R.string.card_selection_credit_limit ,
                options = listOf(
                    "500" to "$500 USD",
                    "1000" to "$1,000 USD",
                    "2500" to "$2,500 USD",
                    "5000" to "$5,000 USD",
                    "10000" to "$10,000 USD"
                ),
                selectedCode = uiState.creditLimit,
                onOptionSelected = onCreditLimitChange
            )

            Spacer(Modifier.height(16.dp))

            FinanciaButton(
                text = R.string.next,
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
private fun CardSelectionPreview() {
    MaterialTheme {
        CardSelectionScreen(
            uiState = CardSelectionUiState(
                cardType = "GOLD",
                creditLimit = "2500"
            ),
            onCardTypeChange = {},
            onCreditLimitChange = {},
            onContinue = {}
        )
    }
}