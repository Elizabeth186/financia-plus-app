package com.financiaplus.app.feature.origination.presentation.digital_signature

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
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
fun DigitalSignatureRoot(
    navController: NavController,
    viewModel: DigitalSignatureViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.Summary.route)
            viewModel.onNavigated()
        }
    }

    DigitalSignatureScreen(
        uiState = uiState,
        onSign = viewModel::onSign,
        onClearSignature = viewModel::onClearSignature,
        onAcceptTermsChange = viewModel::onAcceptTermsChange,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun DigitalSignatureScreen(
    uiState: DigitalSignatureUiState,
    onSign: () -> Unit,
    onClearSignature: () -> Unit,
    onAcceptTermsChange: (Boolean) -> Unit,
    onContinue: () -> Unit
) {
    val paths = remember { mutableStateListOf<Path>() }
    val currentPath = remember { Path() }

    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.DIGITAL_SIGNATURE)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource( R.string.digital_signature_title),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(R.string.digital_signature_indication),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(Color.White, RoundedCornerShape(8.dp))
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    currentPath.moveTo(offset.x, offset.y)
                                },
                                onDrag = { change, _ ->
                                    currentPath.lineTo(change.position.x, change.position.y)
                                    onSign()
                                },
                                onDragEnd = {
                                    paths.add(Path().apply { addPath(currentPath) })
                                    currentPath.reset()
                                }
                            )
                        }
                ) {
                    paths.forEach { path ->
                        drawPath(
                            path = path,
                            color = Color.Black,
                            style = Stroke(
                                width = 3f,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                    drawPath(
                        path = currentPath,
                        color = Color.Black,
                        style = Stroke(
                            width = 3f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                if (!uiState.hasSigned) {
                    Text(
                        text = stringResource( R.string.digital_signature_into_capture),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            FinanciaOutlinedButton(
                text = R.string.digital_signature_clear,
                onClick = {
                    paths.clear()
                    currentPath.reset()
                    onClearSignature()
                },
                enabled = uiState.hasSigned
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.acceptedTerms,
                    onCheckedChange = onAcceptTermsChange
                )
                Text(
                    text = stringResource( R.string.digital_signature_accept_terms),
                    style = MaterialTheme.typography.bodySmall,
                    textDecoration = TextDecoration.Underline,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(8.dp))

            FinanciaButton(
                text = R.string.next,
                onClick = onContinue,
                enabled = uiState.canContinue,
                isLoading = uiState.isLoading
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DigitalSignaturePreview() {
    MaterialTheme {
        DigitalSignatureScreen(
            uiState = DigitalSignatureUiState(hasSigned = true, acceptedTerms = true),
            onSign = {},
            onClearSignature = {},
            onAcceptTermsChange = {},
            onContinue = {}
        )
    }
}