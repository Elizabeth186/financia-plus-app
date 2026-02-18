package com.financiaplus.app.feature.onboarding.presentation.personal_data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.financiaplus.app.R
import com.financiaplus.app.core.navigation.Screen
import com.financiaplus.app.core.navigation.StepData
import com.financiaplus.app.core.ui.FinanciaButton
import com.financiaplus.app.core.ui.FinanciaDropdown
import com.financiaplus.app.core.ui.FinancialTextField
import com.financiaplus.app.core.ui.FinancialTopBar
import com.financiaplus.app.core.utils.Masks

@Composable
fun PersonalDataRoot(
    navController: NavController,
    viewModel: PersonalDataViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.navigateToNext) {
        if (uiState.navigateToNext) {
            navController.navigate(Screen.SelfieCapture.route) {
                launchSingleTop = true
            }
            viewModel.onNavigated()
        }
    }

    PersonalDataScreen(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onAddressChange = viewModel::onAddressChange,
        onGenderChange = viewModel::onGenderChange,
        onContinue = viewModel::onContinue
    )
}

@Composable
fun PersonalDataScreen(
    uiState: PersonalDataUiState,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    Scaffold(
        topBar = {
            FinancialTopBar(step = StepData.PERSONAL_DATA)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            if (uiState.fullName.isNotBlank()) {
                Text(
                    text = stringResource(R.string.personal_data_document) ,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FinancialTextField(
                    value = uiState.fullName,
                    onValueChange = {},
                    label =  R.string.personal_data_full_name,
                    enabled = false
                )
                FinancialTextField(
                    value = uiState.birthDate,
                    onValueChange = {},
                    label = R.string.personal_data_birth_date ,
                    enabled = false
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.personal_data_contact),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            FinancialTextField(
                value = uiState.email,
                onValueChange = onEmailChange,
                label = R.string.personal_data_email ,
                keyboardType = KeyboardType.Email
            )
            FinancialTextField(
                value = uiState.phone,
                onValueChange = { onPhoneChange(Masks.phoneSv(it)) },
                label = R.string.personal_data_phone ,
                keyboardType = KeyboardType.Phone
            )
            FinancialTextField(
                value = uiState.address,
                onValueChange = onAddressChange,
                label =  R.string.personal_data_address
            )
            FinanciaDropdown(
                label = R.string.personal_data_gender ,
                options = listOf(
                    "M" to "Masculino",
                    "F" to "Femenino",
                    "O" to "Otro"
                ),
                selectedCode = uiState.gender,
                onOptionSelected = onGenderChange
            )

            Spacer(modifier = Modifier.height(8.dp))

            FinanciaButton(
                text =  R.string.next ,
                onClick = onContinue,
                enabled = uiState.isFormValid,
                isLoading = uiState.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonalDataPreview() {
    MaterialTheme {
        PersonalDataScreen(
            uiState = PersonalDataUiState(
                fullName = "Ana Pérez",
                birthDate = "1985-05-10"
            ),
            onEmailChange = {},
            onPhoneChange = {},
            onAddressChange = {},
            onGenderChange = {},
            onContinue = {}
        )
    }
}

