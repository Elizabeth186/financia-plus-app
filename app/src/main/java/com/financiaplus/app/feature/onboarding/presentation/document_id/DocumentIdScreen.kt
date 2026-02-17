package com.financiaplus.app.feature.onboarding.presentation.document_id

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.Screen
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.ui.FinanciaButton
import com.financiaplus.app.core.ui.FinancialTextField
import com.financiaplus.app.core.ui.FinancialTopBar

@Composable
fun DocumentIdRoot(
    navController: NavController,
    viewModel: DocumentIdViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.AmlValidation.route)
        }
    }

    DocumentIdScreen(
        uiState = uiState,
        onDocumentIdChanged = viewModel::onDocumentIdChanged,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun DocumentIdScreen(
    uiState: DocumentIdUiState,
    onDocumentIdChanged: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.DOCUMENT_ID)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.document_id_subtitle),
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = stringResource(R.string.document_id_indication),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(8.dp))
            FinancialTextField(
                value = uiState.documentId,
                onValueChange = onDocumentIdChanged,
                label = R.string.document_id_number,
                isError = uiState.error != null,
                errorMessage = uiState.error.toString()
            )
            Spacer(Modifier.weight(1f))
            FinanciaButton(
                text = R.string.next,
                onClick = onContinue,
                enabled = uiState.documentId.isNotBlank(),
                isLoading = uiState.isLoading
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DocumentIdPreview() {
    MaterialTheme {
        DocumentIdScreen(
            uiState = DocumentIdUiState(documentId = "12345678-9"),
            onDocumentIdChanged = {},
            onContinue = {}
        )
    }
}