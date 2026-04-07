package com.example.a176lablearnandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a176lablearnandroid.ui.theme._176LabLearnAndroidTheme

class Part8Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _176LabLearnAndroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProfileScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    // ใช้ BoxWithConstraints เพื่อตรวจสอบขนาดของหน้าจอ (maxWidth)
    BoxWithConstraints(modifier = modifier.fillMaxSize().padding(24.dp)) {
        if (maxWidth < 600.dp) {
            // ความกว้างน้อยกว่า 600dp (โหมดแนวตั้ง/มือถือ) -> ใช้ Column (เรียงบนลงล่าง)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImage()
                Spacer(modifier = Modifier.height(24.dp))
                ProfileInfo()
            }
        } else {
            // ความกว้างมากกว่า 600dp (โหมดแนวนอน/แท็บเล็ต) -> ใช้ Row (เรียงซ้ายไปขวา)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage()
                Spacer(modifier = Modifier.width(48.dp))
                Box(modifier = Modifier.weight(1f)) {
                    ProfileInfo()
                }
            }
        }
    }
}

@Composable
fun ProfileImage() {
    Box(
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "รูปโปรไฟล์", color = Color.DarkGray)
    }
}

@Composable
fun ProfileInfo() {
    Column {
        Text(
            text = "ข้อมูลส่วนตัว",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "ชื่อ: สมชาย ใจดี\nอายุ: 25\nอาชีพ: Android Developer\n\nนี่คือตัวอย่างการทำหน้าจอแบบ Responsive Design ที่สามารถปรับโครงสร้าง (Layout) อัตโนมัติเมื่อหมุนหน้าจอระหว่างแนวตั้งและแนวนอน โดยอ้างอิงจาก maxWidth",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview9() {
    _176LabLearnAndroidTheme {
        Greeting("Android")
    }
}