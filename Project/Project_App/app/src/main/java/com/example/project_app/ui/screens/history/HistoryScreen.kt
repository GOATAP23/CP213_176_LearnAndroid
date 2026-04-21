package com.example.project_app.ui.screens.history

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.project_app.R
import com.example.project_app.data.local.entity.MaintenanceTypes
import com.example.project_app.data.local.entity.ExpenseTypes
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigateBack: () -> Unit,
    onEditRecord: (recordId: Int, isMaintenance: Boolean) -> Unit
) {
    val allRecords by viewModel.records.collectAsState()
    val filteredRecords = viewModel.getFilteredRecords(allRecords)
    val chartData = viewModel.getMonthlyChartData(allRecords)

    val allFilters = listOf(
        MaintenanceTypes.OIL_CHANGE, MaintenanceTypes.BRAKE_PADS,
        MaintenanceTypes.TIRES, MaintenanceTypes.BATTERY,
        ExpenseTypes.FUEL, ExpenseTypes.TOLL, ExpenseTypes.PARKING
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.history_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Chart Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.chart_title), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                ExpenseChartSection(chartData)
            }

            // Search Bar
            item {
                OutlinedTextField(
                    value = viewModel.searchQuery,
                    onValueChange = { viewModel.searchQuery = it },
                    label = { Text(stringResource(R.string.search_records)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }

            // Filter Chips
            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = viewModel.selectedFilter == null,
                            onClick = { viewModel.selectedFilter = null },
                            label = { Text(stringResource(R.string.filter_all)) },
                            leadingIcon = if (viewModel.selectedFilter == null) {
                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                            } else null
                        )
                    }
                    items(allFilters) { filter ->
                        val label = getTypeLabel(filter)
                        FilterChip(
                            selected = viewModel.selectedFilter == filter,
                            onClick = {
                                viewModel.selectedFilter = if (viewModel.selectedFilter == filter) null else filter
                            },
                            label = { Text(label) }
                        )
                    }
                }
            }

            // Record Count
            item {
                Text(
                    "${stringResource(R.string.total_records)}: ${filteredRecords.size}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Records List
            if (filteredRecords.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(stringResource(R.string.no_records_yet), color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            } else {
                items(filteredRecords, key = { "${it.isMaintenance}_${it.id}" }) { record ->
                    HistoryRecordCard(
                        record = record,
                        onEdit = { onEditRecord(record.id, record.isMaintenance) },
                        onDelete = { viewModel.deleteRecord(record) }
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HistoryRecordCard(
    record: HistoryRecord,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val dateFormatter = SimpleDateFormat("dd MMM yyyy", Locale("th", "TH"))
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("th", "TH"))
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.delete_record_title)) },
            text = { Text(stringResource(R.string.delete_record_confirm)) },
            confirmButton = {
                TextButton(onClick = { onDelete(); showDeleteDialog = false }) {
                    Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.cancel_btn))
                }
            }
        )
    }

    Card(
        modifier = Modifier.fillMaxWidth().clickable { onEdit() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (record.isMaintenance) Icons.Default.Build else Icons.Default.LocalGasStation,
                contentDescription = null,
                tint = if (record.isMaintenance) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(getTypeLabel(record.type), fontWeight = FontWeight.SemiBold)
                Text(dateFormatter.format(Date(record.date)), style = MaterialTheme.typography.bodySmall)
                if (record.mileage != null) {
                    Text("${NumberFormat.getNumberInstance().format(record.mileage)} km",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Text(
                currencyFormatter.format(record.amount),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = { showDeleteDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
fun ExpenseChartSection(data: List<MonthlyChartData>) {
    val maxValue = data.maxOfOrNull { it.maintenanceTotal + it.expenseTotal }?.coerceAtLeast(1.0) ?: 1.0

    Card(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            data.forEach { month ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    val maintHeight = ((month.maintenanceTotal / maxValue) * 120).dp
                    val expHeight = ((month.expenseTotal / maxValue) * 120).dp

                    if (month.maintenanceTotal > 0) {
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(maintHeight.coerceAtLeast(2.dp))
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(MaterialTheme.colorScheme.tertiary)
                        )
                    }
                    if (month.expenseTotal > 0) {
                        Box(
                            modifier = Modifier
                                .width(16.dp)
                                .height(expHeight.coerceAtLeast(2.dp))
                                .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                .background(MaterialTheme.colorScheme.primary)
                        )
                    }
                    if (month.maintenanceTotal == 0.0 && month.expenseTotal == 0.0) {
                        Box(modifier = Modifier.width(16.dp).height(2.dp)
                            .background(MaterialTheme.colorScheme.outlineVariant))
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(month.monthLabel, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }

    // Legend
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.tertiary))
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.tab_maintenance), style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(12.dp).clip(RoundedCornerShape(2.dp))
                .background(MaterialTheme.colorScheme.primary))
            Spacer(modifier = Modifier.width(4.dp))
            Text(stringResource(R.string.tab_expense), style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
fun getTypeLabel(type: String): String {
    return when (type) {
        MaintenanceTypes.OIL_CHANGE -> stringResource(R.string.oil_change)
        MaintenanceTypes.BRAKE_PADS -> stringResource(R.string.brake_pads)
        MaintenanceTypes.TIRES -> stringResource(R.string.tires)
        MaintenanceTypes.BATTERY -> stringResource(R.string.battery)
        ExpenseTypes.FUEL -> stringResource(R.string.fuel)
        ExpenseTypes.TOLL -> stringResource(R.string.toll)
        ExpenseTypes.PARKING -> stringResource(R.string.parking)
        MaintenanceTypes.OTHER, ExpenseTypes.OTHER -> stringResource(R.string.other)
        else -> type
    }
}
