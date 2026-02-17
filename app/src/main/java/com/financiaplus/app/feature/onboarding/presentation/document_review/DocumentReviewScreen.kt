package com.financiaplus.app.feature.onboarding.presentation.document_review

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
fun DocumentReviewRoot(
    navController: NavController,
    viewModel: DocumentReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.PersonalData.route)
            viewModel.onNavigated()
        }
    }

    DocumentReviewScreen(
        uiState = uiState,
        onConfirm = viewModel::onConfirm
    )
}

@Composable
fun DocumentReviewScreen(
    uiState: DocumentReviewUiState,
    onConfirm: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.DOCUMENT_REVIEW)
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
                text = stringResource(R.string.doc_review_data),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.doc_review_data_detail) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            uiState.documentImageRes?.let { resStr ->
                val resId = resStr.toIntOrNull()
                if (resId != null) {
                    Image(
                        painter = painterResource(id = resId),
                        contentDescription = stringResource(R.string.doc_captured) ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1.6f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
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
                    DataRow(label = stringResource(R.string.doc_review_id), value = uiState.documentId.ifBlank { "No disponible" })
                    HorizontalDivider()
                    DataRow(label = stringResource(R.string.doc_review_name) , value = uiState.fullName.ifBlank { "No disponible" })
                    HorizontalDivider()
                    DataRow(label = stringResource(R.string.doc_review_birth_date) , value = uiState.birthDate.ifBlank { "No disponible" })
                }
            }

            Spacer(Modifier.weight(1f))

            FinanciaButton(
                text = R.string.doc_review_confirm,
                onClick = onConfirm
            )
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
private fun DocumentReviewPreview() {
    MaterialTheme {
        DocumentReviewScreen(
            uiState = DocumentReviewUiState(
                documentId = "12345678-9",
                fullName = "Ana Pérez",
                birthDate = "1985-05-10",
                documentImageRes = null
            ),
            onConfirm = {}
        )
    }
}