package com.financiaplus.app.feature.onboarding.presentation.selfie_capture

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.Screen
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.ui.FinanciaButton
import com.financiaplus.app.core.ui.FinanciaOutlinedButton
import com.financiaplus.app.core.ui.FinancialTopBar

@Composable
fun SelfieCaptureRoot(
    navController: NavController,
    viewModel: SelfieCaptureViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
          navController.navigate(Screen.BiometricValidation.route)
            viewModel.onNavigated()
        }
    }

    SelfieCaptureScreen(
        uiState = uiState,
        onCapture = viewModel::onCapture,
        onAccept = viewModel::onAccept,
        onRetry = viewModel::onRetry,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun SelfieCaptureScreen(
    uiState: SelfieCaptureUiState,
    onCapture: () -> Unit,
    onAccept: () -> Unit,
    onRetry: () -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.SELFIE_CAPTURE)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(R.string.selfie_take_verify) ,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.selfie_indication) ,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(260.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.currentImage != null) {
                    Image(
                        painter = painterResource(id = uiState.currentImage.drawableRes),
                        contentDescription = stringResource(R.string.selfie_captured) ,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = stringResource(R.string.selfie_into_capture) ,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            uiState.currentImage?.let { image ->
                val qualityColor = if (image.isAcceptable) Color(0xFF2E7D32) else Color(0xFFC62828)
                Text(
                    text = "${image.qualityPercent}% — ${image.qualityLabel}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium,
                    color = qualityColor,
                    textAlign = TextAlign.Center
                )
            }

            uiState.qualityError?.let {
                Text(
                    text = stringResource( it),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(Modifier.weight(1f))

            when {
                !uiState.isCaptured -> {
                    FinanciaButton(
                        text = R.string.selfie_take_capture,
                        onClick = onCapture
                    )
                }
                uiState.isAccepted -> {
                    FinanciaButton(
                        text = R.string.next ,
                        onClick = onContinue
                    )
                }
                else -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        FinanciaOutlinedButton(
                            text = R.string.retry ,
                            onClick = onRetry,
                            modifier = Modifier.weight(1f)
                        )
                        FinanciaButton(
                            text = R.string.accept ,
                            onClick = onAccept,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SelfieCaptureEmptyPreview() {
    MaterialTheme {
        SelfieCaptureScreen(
            uiState = SelfieCaptureUiState(),
            onCapture = {},
            onAccept = {},
            onRetry = {},
            onContinue = {}
        )
    }
}

