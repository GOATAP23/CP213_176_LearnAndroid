package com.example.project_app.ui.screens.add_record

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.project_app.R
import com.example.project_app.data.local.entity.MaintenanceTypes
import com.example.project_app.data.local.entity.ExpenseTypes
import com.example.project_app.ui.screens.history.getTypeLabel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    viewModel: AddRecordViewModel,
    onNavigateBack: () -> Unit
) {
    val tabs = listOf(stringResource(R.string.tab_maintenance), stringResource(R.string.tab_expense))
    val selectedTabIndex = if (viewModel.selectedTab == RecordType.MAINTENANCE) 0 else 1
    val context = LocalContext.current

    // State จัดการ Error และแจ้งเตือน
    var showErrorState by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (viewModel.isEditMode) stringResource(R.string.edit_record_title) else stringResource(R.string.add_record_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = {
            // ปุ่ม Sticky Bottom
            Surface(
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding()
                        .imePadding()
                ) {
                    Button(
                        onClick = {
                            // Validation ตรวจสอบช่องว่าง
                            val isValid = if (viewModel.selectedTab == RecordType.MAINTENANCE) {
                                viewModel.maintMileage.isNotBlank() && viewModel.maintCost.isNotBlank()
                            } else {
                                viewModel.expAmount.isNotBlank()
                            }

                            if (isValid) {
                                showErrorState = false
                                viewModel.saveRecord {
                                    coroutineScope.launch {
                                        val message = if (viewModel.selectedTab == RecordType.MAINTENANCE) {
                                            context.getString(R.string.maintenance_saved)
                                        } else {
                                            context.getString(R.string.expense_saved)
                                        }
                                        val result = snackbarHostState.showSnackbar(
                                            message = message,
                                            actionLabel = context.getString(R.string.undo_btn),
                                            duration = SnackbarDuration.Short
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            // กด Undo → ลบรายการที่เพิ่งบันทึกออก
                                            viewModel.undoLastRecord {
                                                // ไม่ต้องทำอะไร — อยู่หน้าเดิมให้กรอกใหม่
                                            }
                                        } else {
                                            onNavigateBack()
                                        }
                                    }
                                }
                            } else {
                                showErrorState = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(stringResource(R.string.save_record), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // PrimaryTabRow
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { 
                            showErrorState = false
                            viewModel.selectedTab = if(index == 0) RecordType.MAINTENANCE else RecordType.EXPENSE 
                        },
                        text = { Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp) }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // ส่วนของช่องวันที่
                DatePickerField(
                    dateMillis = viewModel.selectedDateMillis,
                    onDateSelected = { viewModel.selectedDateMillis = it }
                )

                // Animation Crossfade ตอนสลับ Tab
                Crossfade(
                    targetState = viewModel.selectedTab,
                    animationSpec = tween(durationMillis = 300),
                    label = "tab_transition"
                ) { currentTab ->
                    
                    if (currentTab == RecordType.MAINTENANCE) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(stringResource(R.string.maintenance_type_label), style = MaterialTheme.typography.labelLarge)
                                Row(
                                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    viewModel.maintenanceOptions.forEach { option ->
                                        val selected = viewModel.maintType == option
                                        FilterChip(
                                            selected = selected,
                                            onClick = { viewModel.maintType = option },
                                            label = { Text(getTypeLabel(option)) },
                                            leadingIcon = if (selected) {
                                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                                            } else null
                                        )
                                    }
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                val isMileageError = showErrorState && viewModel.maintMileage.isBlank()
                                val isCostError = showErrorState && viewModel.maintCost.isBlank()
                                
                                OutlinedTextField(
                                    value = viewModel.maintMileage,
                                    onValueChange = { viewModel.maintMileage = it },
                                    label = { Text(stringResource(R.string.mileage_field)) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = isMileageError,
                                    supportingText = if (isMileageError) { { Text(stringResource(R.string.field_required), color = MaterialTheme.colorScheme.error) } } else null
                                )
                                OutlinedTextField(
                                    value = viewModel.maintCost,
                                    onValueChange = { viewModel.maintCost = it },
                                    label = { Text(stringResource(R.string.cost_field)) },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = isCostError,
                                    supportingText = if (isCostError) { { Text(stringResource(R.string.field_required), color = MaterialTheme.colorScheme.error) } } else null
                                )
                            }
                            
                            TipCard(title = stringResource(R.string.tip_title), message = stringResource(R.string.tip_maintenance))
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text(stringResource(R.string.expense_type_label), style = MaterialTheme.typography.labelLarge)
                                Row(
                                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    viewModel.expenseOptions.forEach { option ->
                                        val selected = viewModel.expType == option
                                        FilterChip(
                                            selected = selected,
                                            onClick = { viewModel.expType = option },
                                            label = { Text(getTypeLabel(option)) },
                                            leadingIcon = if (selected) {
                                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                                            } else null
                                        )
                                    }
                                }
                            }

                            val isAmountError = showErrorState && viewModel.expAmount.isBlank()
                            OutlinedTextField(
                                value = viewModel.expAmount,
                                onValueChange = { viewModel.expAmount = it },
                                label = { Text(stringResource(R.string.amount_field)) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                isError = isAmountError,
                                supportingText = if (isAmountError) { { Text(stringResource(R.string.enter_amount_error), color = MaterialTheme.colorScheme.error) } } else null
                            )

                            // Fuel Economy Fields — แสดงเฉพาะเมื่อเลือก "ค่าน้ำมัน"
                            if (viewModel.expType == ExpenseTypes.FUEL) {
                                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                    OutlinedTextField(
                                        value = viewModel.fuelLiters,
                                        onValueChange = { viewModel.fuelLiters = it },
                                        label = { Text(stringResource(R.string.liters_field)) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next),
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    OutlinedTextField(
                                        value = viewModel.fuelMileage,
                                        onValueChange = { viewModel.fuelMileage = it },
                                        label = { Text(stringResource(R.string.mileage_at_fill)) },
                                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                        modifier = Modifier.weight(1f),
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                }
                            }

                            TipCard(title = stringResource(R.string.tip_title), message = stringResource(R.string.tip_expense))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.notes,
                    onValueChange = { viewModel.notes = it },
                    label = { Text(stringResource(R.string.notes_field)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    minLines = 3 
                )

                // Photo Attachment
                val photoLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.PickVisualMedia()
                ) { uri: Uri? -> viewModel.imageUri = uri }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (viewModel.imageUri != null) {
                            AsyncImage(
                                model = viewModel.imageUri,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        Icon(Icons.Default.PhotoCamera, contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.width(8.dp))
                        TextButton(onClick = {
                            photoLauncher.launch(
                                androidx.activity.result.PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }) {
                            Text(if (viewModel.imageUri != null) stringResource(R.string.change_photo) else stringResource(R.string.attach_photo))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

// Custom DatePicker สไตล์ Material 3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(dateMillis: Long, onDateSelected: (Long) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val dateString = formatter.format(Date(dateMillis))

    OutlinedTextField(
        value = dateString,
        onValueChange = {},
        readOnly = true, 
        label = { Text(stringResource(R.string.date_field)) },
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = stringResource(R.string.date_field))
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDialog) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = dateMillis,
            // Bug #3 Fix: จำกัดไม่ให้เลือกวันที่ในอนาคต
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }
                override fun isSelectableYear(year: Int): Boolean {
                    val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
                    return year <= currentYear
                }
            }
        )
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                    showDialog = false
                }) { Text(stringResource(R.string.confirm_btn)) }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text(stringResource(R.string.cancel_btn)) }
            }
        ) {
            DatePicker(state = datePickerState) 
        }
    }
}

@Composable
fun TipCard(title: String, message: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(message, color = MaterialTheme.colorScheme.onSecondaryContainer, style = MaterialTheme.typography.bodySmall)
        }
    }
}
