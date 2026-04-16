package com.example.project_app.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_app.R

@Composable
fun MainHomeScreen(
    onNavigateToDashboard: () -> Unit,
    onNavigateToTutorial: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.DirectionsCar,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "iDrive",
                fontSize = 56.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 2.sp
            )
            Text(
                text = stringResource(R.string.home_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(80.dp))

            Button(
                onClick = onNavigateToDashboard,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.DirectionsCar, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(stringResource(R.string.home_btn_start), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            FilledTonalButton(
                onClick = onNavigateToTutorial,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.HelpOutline, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(stringResource(R.string.home_btn_tutorial), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            FilledTonalButton(
                onClick = onNavigateToSettings,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(Icons.Default.Settings, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(stringResource(R.string.home_btn_settings), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
