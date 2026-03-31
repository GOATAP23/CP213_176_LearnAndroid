package com.example.a176lablearnandroid.utils

import android.content.Context
import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SharedPreferencesUtilTest {

    private lateinit var mockContext: Context
    private lateinit var mockPrefs: SharedPreferences
    private lateinit var mockEditor: SharedPreferences.Editor

    @Before
    fun setUp() {
        mockContext = mockk()
        mockPrefs = mockk()
        mockEditor = mockk(relaxed = true) // relaxed = true ให้ค่าเริ่มต้นไม่ต้อง mock ทุกตัวที่ ret type เป็น Unit

        // จำลองให้ getSharedPreferences คืนค่า mockPrefs เมื่อ context.getSharedPreferences ถูกเรียก
        every { mockContext.getSharedPreferences("my_app_prefs", Context.MODE_PRIVATE) } returns mockPrefs
        
        // จำลองให้คืนค่า Editor
        every { mockPrefs.edit() } returns mockEditor
        
        // ชี้เป้าให้ Editor function ทำงานได้ (chain methods)
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.putInt(any(), any()) } returns mockEditor
        every { mockEditor.putBoolean(any(), any()) } returns mockEditor

        // เรียกใช้งาน function init จาก SharedPreferencesUtil ก่อนแต่ละเทส
        SharedPreferencesUtil.init(mockContext)
    }

    @Test
    fun saveString_callsPutStringAndApply() {
        // Act: บันทึกข้อมูลผ่าน Utils
        SharedPreferencesUtil.saveString("testKeyString", "testValue")

        // Assert: ตรวจสอบว่า sharedPreferences.edit().putString().apply() ถูกเรียกหรือไม่
        verify { mockEditor.putString("testKeyString", "testValue") }
        verify(exactly = 1) { mockEditor.apply() }
    }

    @Test
    fun getString_returnsMockedValue() {
        // Arrange: สมมติว่าเคยเซฟค่าไว้แล้ว ให้มันส่งคืนมาเหมือนดึงจากไฟล์สำเร็จ
        every { mockPrefs.getString("testKeyString", "") } returns "Mocked Hello"

        // Act: ดึงข้อมูลผ่าน Utils
        val result = SharedPreferencesUtil.getString("testKeyString", "")

        // Assert: ควรจะได้ค่าคืนเป็น "Mocked Hello" เหมือนตอนสมมติไว้ข้างบน (mock)
        assertEquals("Mocked Hello", result)
    }

    @Test
    fun clearAll_callsClearAndApply() {
        // Arrange
        every { mockEditor.clear() } returns mockEditor

        // Act
        SharedPreferencesUtil.clearAll()

        // Assert: Verify check ว่ามีการ clear ข้อมูลทุกอย่าง
        verify(exactly = 1) { mockEditor.clear() }
        verify(exactly = 1) { mockEditor.apply() }
    }
}
