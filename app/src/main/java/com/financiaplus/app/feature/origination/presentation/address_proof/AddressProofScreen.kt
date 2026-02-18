package com.financiaplus.app.feature.origination.presentation.address_proof

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
fun AddressProofRoot(
    navController: NavController,
    viewModel: AddressProofViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.CardSelection.route)
            viewModel.onNavigated()
        }
    }

    AddressProofScreen(
        uiState = uiState,
        onUploadDocument = viewModel::onUploadDocument,
        onRemoveDocument = viewModel::onRemoveDocument,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun AddressProofScreen(
    uiState: AddressProofUiState,
    onUploadDocument: () -> Unit,
    onRemoveDocument: () -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.ADDRESS_PROOF)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.proof_update_doc) ,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.proof_update_doc_detail) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (uiState.hasDocument && uiState.documentName != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(32.dp)
                            )
                            Column {
                                Text(
                                    text = stringResource(R.string.proof_update_doc_loaded) ,
                                    style = MaterialTheme.typography.bodySmall,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = uiState.documentName,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        IconButton(onClick = onRemoveDocument) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.proof_update_doc_remove)
                            )
                        }
                    }
                }
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Create,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = stringResource(R.string.proof_update_doc_upload_dont_exist) ,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))

            if (!uiState.hasDocument) {
                FinanciaButton(
                    text = R.string.proof_update_doc_upload,
                    onClick = onUploadDocument
                )
            } else {
                FinanciaButton(
                    text = R.string.next ,
                    onClick = onContinue
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddressProofEmptyPreview() {
    MaterialTheme {
        AddressProofScreen(
            uiState = AddressProofUiState(),
            onUploadDocument = {},
            onRemoveDocument = {},
            onContinue = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AddressProofWithDocPreview() {
    MaterialTheme {
        AddressProofScreen(
            uiState = AddressProofUiState(
                hasDocument = true,
                documentName = "comprobante_domicilio.pdf"
            ),
            onUploadDocument = {},
            onRemoveDocument = {},
            onContinue = {}
        )
    }
}