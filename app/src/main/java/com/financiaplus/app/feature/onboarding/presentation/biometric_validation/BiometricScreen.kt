package com.financiaplus.app.feature.onboarding.presentation.biometric_validation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.Screen
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.ui.FinanciaButton
import com.financiaplus.app.core.ui.FinancialTopBar

@Composable
fun BiometricValidationRoot(
    navController: NavController,
    viewModel: BiometricValidationViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.GeoLocation.route)
            viewModel.onNavigated()
        }
    }

    BiometricValidationScreen(
        uiState = uiState,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun BiometricValidationScreen(
    uiState: BiometricValidationUiState,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.BIOMETRIC)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.size(72.dp))
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = stringResource(id = R.string.biometric_loading),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.biometric_loading_detail),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                }

                uiState.isApproved -> {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF2E7D32),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "${uiState.matchPercent}%",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.biometric_approved),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(id = R.string.biometric_approved_detail),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(32.dp))
                    FinanciaButton(
                        text = R.string.next,
                        onClick = onContinue
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BiometricLoadingPreview() {
    MaterialTheme {
        BiometricValidationScreen(
            uiState = BiometricValidationUiState(isLoading = true),
            onContinue = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BiometricApprovedPreview() {
    MaterialTheme {
        BiometricValidationScreen(
            uiState = BiometricValidationUiState(
                isLoading = false,
                matchPercent = 94,
                isApproved = true
            ),
            onContinue = {}
        )
    }
}