package com.financiaplus.app.feature.origination.presentation.summary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.Screen
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.ui.FinanciaButton
import com.financiaplus.app.core.ui.FinancialTopBar

@Composable
fun SummaryRoot(
    navController: NavController,
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.Result.route)
            viewModel.onNavigated()
        }
    }

    SummaryScreen(
        uiState = uiState,
        onConfirm = viewModel::onConfirm
    )
}

@Composable
fun SummaryScreen(
    uiState: SummaryUiState,
    onConfirm: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.SUMMARY)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.summary_review_info) ,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.summary_details) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.summary_personal_data),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                    DataRow(stringResource(R.string.summary_full_name) , uiState.fullName)
                    DataRow( stringResource(R.string.summary_document_id), uiState.documentId)
                    DataRow( stringResource(R.string.summary_email), uiState.email)
                    DataRow( stringResource(R.string.summary_phone), uiState.phone)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.summary_economic_profile),
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                    DataRow( stringResource(R.string.summary_monthly_income), "$${uiState.monthlyIncome} USD")
                    DataRow( stringResource(R.string.summary_occupation), uiState.occupation)
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.summary_card_selection) ,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider()
                    DataRow( stringResource(R.string.summary_card_type), uiState.cardType)
                    DataRow( stringResource(R.string.summary_credit_limit), "$${uiState.creditLimit} USD")
                }
            }

            Spacer(Modifier.height(8.dp))

            FinanciaButton(
                text = R.string.summary_confirm ,
                onClick = onConfirm,
                isLoading = uiState.isLoading
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun DataRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryPreview() {
    MaterialTheme {
        SummaryScreen(
            uiState = SummaryUiState(
                fullName = "Sofia Ramirez",
                documentId = "12345678-9",
                email = "sofia@email.com",
                phone = "7890-1234",
                monthlyIncome = "1500",
                occupation = "Desarrollador",
                cardType = "GOLD",
                creditLimit = "2500"
            ),
            onConfirm = {}
        )
    }
}