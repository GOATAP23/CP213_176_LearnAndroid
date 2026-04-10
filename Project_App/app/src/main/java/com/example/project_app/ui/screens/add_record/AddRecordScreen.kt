package com.example.project_app.ui.screens.add_record

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecordScreen(
    viewModel: AddRecordViewModel,
    onNavigateBack: () -> Unit
) {
    val tabs = listOf("การดูแลรักษารถ", "ค่าใช้จ่ายทั่วไป")
    val selectedTabIndex = if (viewModel.selectedTab == RecordType.MAINTENANCE) 0 else 1

    // State จัดการ Error และแจ้งเตือน
    var showErrorState by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("บันทึกข้อมูล") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "กลับ")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // เตรียมจุดโชว์ Snackbar
        bottomBar = {
            // ปุ่ม Sticky Bottom แช่ล่างสุด และลอยเหนือคีย์บอร์ดตอนพิมพ์
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
                                    // โชว์ Snackbar เมื่อบันทึกสำเร็จ
                                    coroutineScope.launch {
                                        val message = if (viewModel.selectedTab == RecordType.MAINTENANCE) "บันทึกการดูแลรถเรียบร้อย" else "บันทึกค่าใช้จ่ายเรียบร้อย"
                                        val result = snackbarHostState.showSnackbar(
                                            message = message,
                                            actionLabel = "เลิกทำ (Undo)",
                                            duration = SnackbarDuration.Short
                                        )
                                        if (result == SnackbarResult.ActionPerformed) {
                                            // TODO: ในสถานการณ์จริงสามารถเรียก viewModel.undoRecord() เพื่อลบออกจาก RoomDB ได้ 
                                            // แต่สำหรับตอนนี้เราแค่กดแล้วรอกรอกใหม่
                                        } else {
                                            onNavigateBack() // ถ้าระยะเวลา Timeout ไปตามปกติ ให้กลับหน้าหลัก
                                        }
                                    }
                                }
                            } else {
                                showErrorState = true // เซ็ตค่าว่ามี Error เพื่อให้ขอบช่องกรอกข้อมูลกลายเป็นสีแดง
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("บันทึกข้อมูล", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // PrimaryTabRow ออกแบบมาเพื่อหน้านี้โดยเฉพาะ ให้เห็นชัดเจนตัดเส้นใต้ Tab
            PrimaryTabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { 
                            showErrorState = false // ล้างระวังค้างตอนสลับ Tab
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
                verticalArrangement = Arrangement.spacedBy(24.dp) // เว้นระยะให้โล่งขึ้น
            ) {
                // ส่วนของช่องวันที่
                DatePickerField(
                    dateMillis = viewModel.selectedDateMillis,
                    onDateSelected = { viewModel.selectedDateMillis = it }
                )

                // ใส่ Animation Crossfade ตอนสลับ Tab เนื้อหาจะเฟดเข้าออกแบบสมูธ
                Crossfade(
                    targetState = viewModel.selectedTab,
                    animationSpec = tween(durationMillis = 300),
                    label = "tab_transition"
                ) { currentTab ->
                    
                    if (currentTab == RecordType.MAINTENANCE) {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            // UX: นำ Filter Chip แบบเม็ดยามาใช้แทน Dropdown (ผู้ใช้กดจิ้มได้เลยใน 1 คลิก)
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("ประเภทการเปลี่ยน/บำรุงรักษา", style = MaterialTheme.typography.labelLarge)
                                Row(
                                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    viewModel.maintenanceOptions.forEach { option ->
                                        val selected = viewModel.maintType == option
                                        FilterChip(
                                            selected = selected,
                                            onClick = { viewModel.maintType = option },
                                            label = { Text(option) },
                                            leadingIcon = if (selected) {
                                                { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(FilterChipDefaults.IconSize)) }
                                            } else null
                                        )
                                    }
                                }
                            }

                            // UX: ยกระดับ Error Handling ถ้ายื่นข้อมูลขอบจะแดงพร้อมมี Supporting Text
                            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                                val isMileageError = showErrorState && viewModel.maintMileage.isBlank()
                                val isCostError = showErrorState && viewModel.maintCost.isBlank()
                                
                                OutlinedTextField(
                                    value = viewModel.maintMileage,
                                    onValueChange = { viewModel.maintMileage = it },
                                    label = { Text("เลขไมล์") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = isMileageError,
                                    supportingText = if (isMileageError) { { Text("ข้อมูลบังคับ", color = MaterialTheme.colorScheme.error) } } else null
                                )
                                OutlinedTextField(
                                    value = viewModel.maintCost,
                                    onValueChange = { viewModel.maintCost = it },
                                    label = { Text("ราคา(บาท)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                    modifier = Modifier.weight(1f),
                                    shape = RoundedCornerShape(12.dp),
                                    isError = isCostError,
                                    supportingText = if (isCostError) { { Text("ข้อมูลบังคับ", color = MaterialTheme.colorScheme.error) } } else null
                                )
                            }
                        }
                    } else {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("หมวดหมู่ค่าใช้จ่าย", style = MaterialTheme.typography.labelLarge)
                                Row(
                                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    viewModel.expenseOptions.forEach { option ->
                                        val selected = viewModel.expType == option
                                        FilterChip(
                                            selected = selected,
                                            onClick = { viewModel.expType = option },
                                            label = { Text(option) },
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
                                label = { Text("จำนวนเงิน (บาท)") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                isError = isAmountError,
                                supportingText = if (isAmountError) { { Text("กรุณากรอกจำนวนเงิน", color = MaterialTheme.colorScheme.error) } } else null
                            )
                        }
                    }
                } // สิ้นสุด Crossfade

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = viewModel.notes,
                    onValueChange = { viewModel.notes = it },
                    label = { Text("โน้ตเพิ่มเติม (ถ้ามี)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    minLines = 3 
                )

                Spacer(modifier = Modifier.height(60.dp))
            }
        }
    }
}

// Custom DatePicker หรูๆ สไตล์ Material 3
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
        label = { Text("วันที่บันทึก (กดเพื่อเปลี่ยน)") },
        shape = RoundedCornerShape(12.dp),
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.DateRange, contentDescription = "เลือกวันที่")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

    if (showDialog) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dateMillis)
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { onDateSelected(it) }
                    showDialog = false
                }) { Text("ตกลง") }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) { Text("ยกเลิก") }
            }
        ) {
            DatePicker(state = datePickerState) 
        }
    }
}
