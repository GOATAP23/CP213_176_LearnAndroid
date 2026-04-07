package com.example.a176lablearnandroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a176lablearnandroid.ui.theme._176LabLearnAndroidTheme

class Part9Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _176LabLearnAndroidTheme {
                CollapsingScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingScreen() {
    // 1. สร้าง ScrollBehavior โดยใช้ exitUntilCollapsedScrollBehavior
    // ซึ่งพฤติกรรมนี้จะจำลองการทำ Collapsing Toolbar (หน้าจอหดเวลารูดลงสุด)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    // 2. ส่งต่อ scrollBehavior เข้าไปใน Scaffold ผ่าน modifier.nestedScroll เพื่อผูกให้ Scaffold ทราบการเลื่อน
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // 3. ใช้ LargeTopAppBar เพื่อสร้างบาร์ด้านบนที่มีพื้นที่สูง
            // scrollBehavior ถูกส่งเข้าไปในคุณสมบัตินี้ ทำให้บาร์หดขยายตัวได้เอง
            LargeTopAppBar(
                title = { Text("Collapsing Concept") },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = innerPadding
        ) {
            item {
                Text(
                    text = """
                        Concept (การหด/ขยาย Toolbar อัตโนมัติ):
                        
                        ใน Jetpack Compose (ตั้งแต่ Material 3) การทำหน้าจอที่มี "Collapsing Toolbar" แบบเดิมๆ ใน XML (CoordinatorLayout, AppBarLayout) นั้นทำได้ง่ายขึ้นมาก โดยใช้ ScrollBehavior ที่เตรียมมาให้
                        
                        ส่วนประกอบสำคัญที่ต้องรวมพลังกันคือ:
                        1. `TopAppBarScrollBehavior` : ตัวจัดการสถานะว่าควรหดหรือขยาย ซึ่งเราสามารถสร้างผ่าน `TopAppBarDefaults` ของ Material 3
                        2. `Modifier.nestedScroll()` : ผูกเข้าไปที่ชั้นนอกสุดอย่าง `Scaffold` เพื่อส่งทอดการจับการเลื่อนหน้าจอจากเนื้อหา (Child) กลับไปให้ตัว `ScrollBehavior` รับทราบสภาพการหมุน/เลื่อนหน้าจอ
                        3. `LargeTopAppBar` (หรือ Medium/Normal ขึ้นอยู่กับว่าอยากได้ขนาดขยายใหญ่สุดแค่ไหน) แล้วส่ง `scrollBehavior` ต่อเข้าไปอีกทอด
                    """.trimIndent(),
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            items(50) { index ->
                Text(
                    text = "Dummy Item ${index + 1} สำหรับทดสอบการเลื่อน (Scroll)",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )
            }
        }
    }
}