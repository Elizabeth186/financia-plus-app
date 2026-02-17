package com.financiaplus.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.financiaplus.app.core.navigation.NavGraph
import com.financiaplus.app.core.navigation.SaveStartRoute
import com.financiaplus.app.core.security.SessionManager
import com.financiaplus.app.ui.theme.FinanciaPlusTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinanciaPlusTheme {
                val navController = rememberNavController()
                val startRoute = SaveStartRoute(sessionManager.getCurrentStep())
                NavGraph(
                    navController = navController,
                    startRoute = startRoute
                )
            }
        }
    }
}