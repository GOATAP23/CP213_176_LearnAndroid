// checking 24/2/2026

package com.example.a176lablearnandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


class MenuActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Log.v("MyTag", "Verbose: ข้อมูลยิบย่อย (เช่น ค่าแกน XYZ จาก Sensor ทุกๆ มิลลิวินาที)")
        // Log.d("MyTag", "Debug: ข้อมูลไว้หาบั๊ก (เช่น ค่า ID ที่ดึงมาจาก Database = 123)")
        // Log.i("MyTag", "Info: แจ้งสถานะทั่วไป (เช่น โหลดข้อมูล API สำเร็จแล้ว)")
        // Log.w("MyTag", "Warn: เตือนว่าแปลกๆ นะ (เช่น โหลดภาพไม่ขึ้น เลยใช้ภาพ Default แทน)")
        // Log.e("MyTag", "Error: พังแล้วจ้า (เช่น catch Exception ได้ หรือ API ร่วง)")

        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Button(onClick = {
                    startActivity(
                        Intent(this@MenuActivity, SensorActivity::class.java)
                    )
                }) {
                    Text(text = "SensorActivity")
                }

                Button(onClick = {
                    startActivity(
                        Intent(this@MenuActivity, GalleryActivity::class.java)
                    )
                }) {
                    Text(text = "GalleryActivity")
                }

                Button(onClick = {
                    startActivity(
                        Intent(this@MenuActivity, RPGCardActivity::class.java)
                    )
                }) {
                    Text(text = "RPGCardActivity")
                }

                Button(onClick = {
                    startActivity(
                        Intent(this@MenuActivity, PokedexActivity::class.java)
                    )
                }) {
                    Text(text = "PokedexActivity")
                }

                Button(onClick = {
                    startActivity(
                        Intent(this@MenuActivity, LifeCycleComposeActivity::class.java)
                    )
                }) {
                    Text(text = "LifeCycleComposeActivity")
                }

                Button(onClick = {
                    startActivity(
                        Intent(this@MenuActivity, SharedPreferencesActivity::class.java)
                    )
                }) {
                    Text(text = "SharedPreferencesActivity")
                }
            }
        }
    }
}
