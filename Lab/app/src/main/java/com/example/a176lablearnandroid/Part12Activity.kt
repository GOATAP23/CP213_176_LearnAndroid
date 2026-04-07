package com.example.a176lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a176lablearnandroid.ui.theme._176LabLearnAndroidTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
class Part12Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _176LabLearnAndroidTheme {
                var showSheet by remember { mutableStateOf(false) }
                var showDialog by remember { mutableStateOf(false) }
                val sheetState = rememberModalBottomSheetState()
                val scope = rememberCoroutineScope()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Part 12: Dialog & Bottom Sheet",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = "Concept:\n- Modal Bottom Sheet: เป็นหน้าต่างที่สไลด์ขึ้นมาจากด้านล่าง มักใช้สำหรับแสดงเมนู ตัวเลือกเสริม หรือข้อมูลรายละเอียดเพิ่มเติมที่ไม่อยากให้บังหน้าจอทั้งหมด\n- Middle Dialog: เป็นหน้าต่างป๊อปอัปกลางหน้าจอ มักใช้ในกรณีที่ต้องการให้ผู้ใช้ตัดสินใจหรือยืนยันอะไรบางอย่างที่สำคัญแบบขัดจังหวะ (Interrupt)",
                            fontSize = 14.sp,
                            modifier = Modifier.padding(bottom = 32.dp)
                        )

                        Button(onClick = { showDialog = true }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Show Middle Dialog")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(onClick = { showSheet = true }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Show Modal Bottom Sheet")
                        }

                        // Middle Dialog Implementation
                        if (showDialog) {
                            AlertDialog(
                                onDismissRequest = { showDialog = false },
                                title = { Text(text = "Important Message") },
                                text = { Text(text = "This is a Middle Dialog. It forces the user to interact with or dismiss it before they can continue using the background app.") },
                                confirmButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text("Confirm")
                                    }
                                },
                                dismissButton = {
                                    TextButton(onClick = { showDialog = false }) {
                                        Text("Dismiss")
                                    }
                                }
                            )
                        }

                        // Modal Bottom Sheet Implementation
                        if (showSheet) {
                            ModalBottomSheet(
                                onDismissRequest = { showSheet = false },
                                sheetState = sheetState
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        // Padding bottom to accommodate status bars in older devices if needed
                                        .padding(bottom = 32.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "This is a Modal Bottom Sheet",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text(text = "You can swipe this down to dismiss it, or click completely outside. It's often used for sharing menus or context actions.")
                                    Spacer(modifier = Modifier.height(32.dp))
                                    Button(onClick = {
                                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                                            if (!sheetState.isVisible) {
                                                showSheet = false
                                            }
                                        }
                                    }) {
                                        Text("Close Sheet")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}