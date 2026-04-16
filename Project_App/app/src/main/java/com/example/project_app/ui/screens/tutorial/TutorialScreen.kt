package com.example.project_app.ui.screens.tutorial

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialScreen(
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.tutorial_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TutorialCard(
                title = stringResource(R.string.tutorial_step1_title),
                desc = stringResource(R.string.tutorial_step1_desc)
            )
            TutorialCard(
                title = stringResource(R.string.tutorial_step2_title),
                desc = stringResource(R.string.tutorial_step2_desc)
            )
            TutorialCard(
                title = stringResource(R.string.tutorial_step3_title),
                desc = stringResource(R.string.tutorial_step3_desc)
            )
        }
    }
}

@Composable
fun TutorialCard(title: String, desc: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Default.CheckCircle, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(desc, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
