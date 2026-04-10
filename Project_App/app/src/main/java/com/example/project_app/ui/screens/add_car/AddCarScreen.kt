package com.example.project_app.ui.screens.add_car

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(
    viewModel: CarViewModel,
    onNavigateBack: () -> Unit 
) {
    val context = LocalContext.current

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
                context.contentResolver.takePersistableUriPermission(uri, flag)
                viewModel.imageUri = uri
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("เพิ่มรถยนต์คันใหม่") })
        },
        bottomBar = {
            // ปุ่มแช่แข็งให้อยู่ล่างสุดเสมอ (Sticky Bottom Button)
            Surface(
                color = MaterialTheme.colorScheme.background,
                shadowElevation = 8.dp // ให้มีเงาหน่อยเพื่อให้เห็นว่าปุ่มมันลอยทับเนื้อหา
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding() // หลบปุ่มโฮมของ Android ด้านล่าง
                        .imePadding() // ถ้าคีย์บอร์ดเด้ง ปุ่มจะเด้งลอยขี่คีย์บอร์ดมาด้วยให้กดง่ายๆ!
                ) {
                    Button(
                        onClick = { viewModel.saveCar(onSuccess = onNavigateBack) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        enabled = viewModel.brand.isNotBlank() && viewModel.model.isNotBlank() 
                    ) {
                        Text("บันทึกข้อมูลรถ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 1. กรอบใส่รูปขนาดใหญ่ (Image Placeholder) เต็มตา
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(24.dp)) // โค้งระดับสุดยอดให้ดูหรู
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable { 
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.imageUri != null) {
                    AsyncImage(
                        model = viewModel.imageUri,
                        contentDescription = "Car image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.AddAPhoto, 
                            contentDescription = "Add image",
                            modifier = Modifier.size(56.dp),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "แตะเพื่ออัปโหลดรูปรถของคุณ", 
                            color = Color.Gray, 
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // 2. ฟิลด์สำคัญที่ถูกเรียงลำดับใหม่
            OutlinedTextField(
                value = viewModel.brand,
                onValueChange = { viewModel.brand = it },
                label = { Text("ยี่ห้อ (เช่น Toyota, Honda)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = viewModel.model,
                onValueChange = { viewModel.model = it },
                label = { Text("รุ่น (เช่น Civic, Yaris)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // จับปีจดทะเบียนและระยะทางมาอยู่บรรทัดเดียวกัน เพื่อประหยัดพื้นที่หน้าจอ
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = viewModel.year,
                    onValueChange = { viewModel.year = it },
                    label = { Text("ปีจดทะเบียน") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                OutlinedTextField(
                    value = viewModel.mileage,
                    onValueChange = { viewModel.mileage = it },
                    label = { Text("ไมล์ปัจจุบัน (km)") },
                    modifier = Modifier.weight(1.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }
            
            // ช่วยเว้นล่างให้ไม่ชนกับ Sticky Button มากเกินไปตอนเลื่อน
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
