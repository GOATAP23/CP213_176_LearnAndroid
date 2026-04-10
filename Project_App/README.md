# Car Maintenance & Expense Tracker (แอปพลิเคชันบันทึกการดูแลรถยนต์)

[![Android API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](#)
[![Kotlin](https://img.shields.io/badge/Language-Kotlin_2.0-blue.svg)](#)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack_Compose-purple.svg)](#)
[![Room Database](https://img.shields.io/badge/Database-Room_DB-red.svg)](#)

An intuitive and modern Android application designed to help car owners manage their vehicles, track maintenance records, and log expenses seamlessly. Built with the Modern Android Development (MAD) tech stack.

---

## ความสร้างสรรค์ (Creativity / Motivation)
แอปพลิเคชันนี้ถูกคิดค้นขึ้นเพื่อแก้ปัญหา **"การลืมบันทึกประวัติการซ่อมบำรุงและค่าใช้จ่ายของรถ"** ที่มักจะจดใส่กระดาษหรือสมุดโน้ตแล้วสูญหายเป็นประจำ ทำให้ผู้ใช้อาจจะจำไม่ได้ว่าเปลี่ยนถ่ายน้ำมันเครื่องครั้งล่าสุดในระยะไมล์เท่าไหร่ หรือเดือนนี้หมดค่าน้ำมันไปกี่บาท แอปนี้นำเสนอหนทางใหม่ในรูปแบบ Digital Notebook ที่ใช้งานง่ายบนสมาร์ทโฟนด้วย User Interface ที่เป็นระเบียบ ทันสมัย และลดความยุ่งยากในการกรอกข้อมูล

## การแก้ปัญหาและการใช้งานจริง (Practical Solutions)
* **ช่วยประหยัดเงิน (Cost Tracking):** ด้วยระบบ Dashboard อัจฉริยะ ผู้ใช้สามารถติดตามค่าน้ำมันและค่าบำรุงรักษาต่างๆ แยกเป็น **"รายเดือน"** และ **"รายปี"** ทำให้มองเห็นภาพรวมและสามารถวางแผนทางการเงินเพื่อประหยัดรายจ่ายได้ดีขึ้น
* **ดันความเสี่ยงรถพัง (Maintenance Prevention):** ด้วยการเก็บประวัติและตัวเลขไมล์อย่างเป็นระบบ (เช่น ยาง ผ้าเบรก แบตเตอรี่) ผู้ใช้สามารถเปิดดูย้อนหลังได้ตลอดเวลาเพื่อหาว่าอะไหล่ชิ้นไหนควรได้รับการเปลี่ยนเมื่อถึงเวลา ช่วยประหยัดค่าซ่อมบำรุงก้อนใหญ่จากการละเลยที่อาจทำให้ชิ้นส่วนอื่นๆ เสียหายตามไปด้วย

## การเรียนรู้จากโปรเจกต์ (Technical Learnings)
โปรเจกต์นี้เป็นส่วนหนึ่งของรายวิชา Android Development ซึ่งได้นำเทคโนโลยีและหลักการพัฒนายอดฮิตในอุตสาหกรรมปัจจุบันมาปรับใช้จริง:
* **Jetpack Compose (Material 3):** เรียนรู้การเขียน UI สมัยใหม่ในรูปแบบ Declarative UI, การจัดการ State (`mutableStateOf`), และการออกแบบธีม (Material Design 3) ให้แอปพลิเคชันดูคลีน เป็นมิตร และลื่นไหลที่สุด
* **MVVM Architecture:** การทำโครงสร้างแอปพลิเคชันที่แยกส่วนประกอบอย่างชัดเจน แบ่งส่วนของฐานข้อมูล (Model), โลจิกประมวลผล (ViewModel), และการแสดงภาพ (UI) ทำให้โค้ดไม่มีความซ้ำซ้อนและซ่อมแซมได้ง่าย
* **Room Database & Coroutines:** เรียนรู้การออกแบบ Local Database เชิงสัมพันธ์ด้วย Room DB การกำหนด DAO และการทำ Asynchronous Programming ร่วมกับดึงข้อมูลแบบ `Flow` ซึ่งช่วยให้ UI ตอบสนองได้แบบ Real-time โดยผู้ใช้ไม่ต้องกดรีเฟรชหน้าจอเลย
* **Jetpack Navigation Compose:** การวางสถาปัตยกรรมเส้นทางของแอปพลิเคชัน (NavGraph) และศึกษาวิธีรับ/ส่ง Parameters ระหว่างหน้าจอ (เช่น `carId`) เพื่อความต่อเนื่องในการใช้งาน

## ฟีเจอร์ในอนาคต (Future Roadmap)
ในเวอร์ชันระดับ MVP (Minimum Viable Product) แอปสามารถใช้งานปมปัญหาหลักได้อย่างครบถ้วน และเราได้วางแผนในการขยายฟีเจอร์เพื่อเพิ่มสเกลในอนาคตดังนี้:
- **Trip Log System:** ฟีเจอร์บันทึกระยะทางและการใช้น้ำมันตาม "ทริปการเดินทาง" อย่างละเอียด สำหรับผู้ที่ต้องการเบิกค่าเดินทางหรือคำนวณอัตรากินน้ำมัน (กม./ลิตร)
- **Cloud Sync (Firebase / Supabase):** ปรับระบบจากการเซฟลงเครื่องเดี่ยวๆ (Local Storage) เป็นการสำรองข้อมูลขึ้น Cloud อัตโนมัติ เพื่อไม่ให้ข้อมูลประวัติรถสูญหายแม้มีการเปลี่ยนสมาร์ทโฟน
- **Smart Reminders:** ระบบ Push Notification แจ้งเตือนเมื่อรถถึงรอบระยะไมล์หรือระยะเวลาที่ควรจะต้องนำรถเข้าอู่ซ่อมบำรุง 

---
*Created as an academic project for an Android Development course.*
