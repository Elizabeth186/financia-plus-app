package com.financiaplus.app.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.financiaplus.app.R

@Composable
fun FinancialTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: Int,
    modifier: Modifier = Modifier.fillMaxWidth(),
    enabled: Boolean = true,
    isError: Boolean = false,
    errorMessage: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(label)) },
        modifier = modifier,
        enabled = enabled,
        isError = isError,
        supportingText = {
            if (isError && errorMessage != null) {
                Text(errorMessage)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun FinancialTextFieldPreview() {
    FinancialTextField(
        value = "",
        onValueChange = {},
        label = R.string.app_name,
        modifier = Modifier,
        enabled = true,
        isError = false,
        errorMessage = null,
        keyboardType = KeyboardType.Text,
        visualTransformation = VisualTransformation.None,
        trailingIcon = null
    )
}