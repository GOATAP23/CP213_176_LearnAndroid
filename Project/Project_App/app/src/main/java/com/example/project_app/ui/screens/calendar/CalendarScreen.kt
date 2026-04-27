package com.example.project_app.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_app.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(
    viewModel: CalendarViewModel,
    onNavigateBack: () -> Unit
) {
    val events by viewModel.events.collectAsState()
    var currentYear by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.YEAR)) }
    var currentMonth by remember { mutableIntStateOf(Calendar.getInstance().get(Calendar.MONTH)) }

    val currentLocale = androidx.compose.ui.platform.LocalConfiguration.current.locales.get(0) ?: java.util.Locale.getDefault()
    val monthNames = remember(currentLocale) {
        (0..11).map { month ->
            java.text.SimpleDateFormat("MMMM", currentLocale).format(
                Calendar.getInstance().apply { set(Calendar.MONTH, month) }.time
            )
        }.toTypedArray()
    }
    val dayNames = remember(currentLocale) {
        arrayOf(Calendar.SUNDAY, Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY).map { day ->
            java.text.SimpleDateFormat("E", currentLocale).format(
                Calendar.getInstance().apply { set(Calendar.DAY_OF_WEEK, day) }.time
            )
        }.toTypedArray()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.calendar_title), fontWeight = FontWeight.Bold) },
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
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Month Navigation
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            if (currentMonth == 0) {
                                currentMonth = 11; currentYear--
                            } else currentMonth--
                        }) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
                        }

                        Text(
                            "${monthNames[currentMonth]} $currentYear",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = {
                            if (currentMonth == 11) {
                                currentMonth = 0; currentYear++
                            } else currentMonth++
                        }) {
                            Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Day Headers
                    Row(modifier = Modifier.fillMaxWidth()) {
                        dayNames.forEach { day ->
                            Text(
                                text = day,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Calendar Grid
                    val cal = Calendar.getInstance()
                    cal.set(currentYear, currentMonth, 1)
                    val firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1
                    val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
                    val today = Calendar.getInstance()
                    val isCurrentMonth = today.get(Calendar.YEAR) == currentYear && today.get(Calendar.MONTH) == currentMonth

                    val totalCells = firstDayOfWeek + daysInMonth
                    val rows = (totalCells + 6) / 7

                    for (row in 0 until rows) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (col in 0..6) {
                                val cellIndex = row * 7 + col
                                val day = cellIndex - firstDayOfWeek + 1

                                if (day in 1..daysInMonth) {
                                    val key = "$currentYear-$currentMonth-$day"
                                    val info = events[key]
                                    val isToday = isCurrentMonth && day == today.get(Calendar.DAY_OF_MONTH)

                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .aspectRatio(1f)
                                            .padding(2.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(
                                                if (isToday) MaterialTheme.colorScheme.primaryContainer
                                                else MaterialTheme.colorScheme.surface
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "$day",
                                                fontSize = 14.sp,
                                                fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                                                color = if (isToday) MaterialTheme.colorScheme.primary
                                                else MaterialTheme.colorScheme.onSurface
                                            )
                                            if (info != null) {
                                                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                                    if (info.maintenanceCount > 0) {
                                                        Box(
                                                            modifier = Modifier.size(6.dp)
                                                                .clip(CircleShape)
                                                                .background(MaterialTheme.colorScheme.tertiary)
                                                        )
                                                    }
                                                    if (info.expenseCount > 0) {
                                                        Box(
                                                            modifier = Modifier.size(6.dp)
                                                                .clip(CircleShape)
                                                                .background(MaterialTheme.colorScheme.primary)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Legend
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.calendar_legend), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.tab_maintenance), style = MaterialTheme.typography.bodySmall)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(10.dp).clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.tab_expense), style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}
