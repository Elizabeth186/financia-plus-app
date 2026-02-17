package com.financiaplus.app.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.StepInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinancialTopBar(
    step: StepInfo,
    onBackClick: (() -> Unit)? = null
) {
    Column {
        TopAppBar(
            title = { Text(text = stringResource(id = step.title)) },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            }
        )
        LinearProgressIndicator(
            progress = { step.current.toFloat() / step.total.toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}