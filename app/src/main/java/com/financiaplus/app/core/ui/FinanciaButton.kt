package com.financiaplus.app.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.financiaplus.app.R

@Composable
fun FinanciaButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        enabled = enabled && !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Text(stringResource(text))
        }
    }
}

@Composable
fun FinanciaOutlinedButton(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(52.dp),
        enabled = enabled
    ) {
        Text(stringResource(text))
    }
}

@Preview(showBackground = true)
@Composable
fun FinanciaButtonPreview(){
    FinanciaButton(
        text = R.string.next,
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        enabled = true,
        isLoading = false
    )
}

@Preview(showBackground = true)
@Composable
fun FinanciaOutlinedButtonPreview(){
    FinanciaOutlinedButton(
        text = R.string.next,
        onClick = {},
        modifier = Modifier.fillMaxWidth(),
        enabled = true
    )
}