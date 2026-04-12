<<<<<<< HEAD
# 🚗 Car Tracker — ระบบดูแลรถยนต์อัจฉริยะ
=======
# Car Maintenance & Expense Tracker (แอปพลิเคชันบันทึกการดูแลรถยนต์)
>>>>>>> f3068b4d7c4a47b4a1c9fef52d08f786ce00dcfd

> **Smart Car Maintenance & Expense Tracker** — แอปพลิเคชัน Android สำหรับบริหารจัดการค่าใช้จ่ายและการดูแลรักษารถยนต์อย่างชาญฉลาด พร้อมระบบแจ้งเตือนเชิงพยากรณ์ (Predictive Alert)

---

<<<<<<< HEAD
## 📋 สารบัญ

- [✨ ความสร้างสรรค์และ Wow Factor](#-ความสร้างสรรค์และ-wow-factor)
- [🎯 การแก้ปัญหาและการใช้งานจริง](#-การแก้ปัญหาและการใช้งานจริง)
- [🏗️ สถาปัตยกรรมและเทคโนโลยี](#️-สถาปัตยกรรมและเทคโนโลยี)
- [📱 ฟีเจอร์หลัก](#-ฟีเจอร์หลัก)
- [📂 โครงสร้างโปรเจกต์](#-โครงสร้างโปรเจกต์)
- [🧠 สิ่งที่ได้เรียนรู้](#-สิ่งที่ได้เรียนรู้)
- [🚀 ฟีเจอร์ในอนาคต](#-ฟีเจอร์ในอนาคต)
- [⚙️ วิธีติดตั้งและรันโปรเจกต์](#️-วิธีติดตั้งและรันโปรเจกต์)
=======
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
>>>>>>> f3068b4d7c4a47b4a1c9fef52d08f786ce00dcfd

---

## ✨ ความสร้างสรรค์และ Wow Factor

### 🔮 Predictive Alert System (ระบบแจ้งเตือนเชิงพยากรณ์)
ฟีเจอร์เด่นที่สุดของแอป — วิเคราะห์ข้อมูลการบำรุงรักษาล่าสุดแบบอัตโนมัติ และแจ้งเตือนเมื่อใกล้ถึงระยะที่ต้องเปลี่ยนอะไหล่:

| สถานะ | สี | ความหมาย |
|--------|------|----------|
| ✅ สมบูรณ์ | 🟢 เขียว | ระยะวิ่งต่ำกว่า 8,000 กม. จากครั้งเปลี่ยนล่าสุด |
| ⚠️ ใกล้ถึง | 🟡 เหลือง | ระยะวิ่ง 8,000-10,000 กม. — ควรเตรียมนัดศูนย์ |
| 🚨 เกินกำหนด | 🔴 แดง | เกิน 10,000 กม. — ต้องดำเนินการทันที |

### 🌙 Dark Mode & Light Mode
- **Light Mode**: คลีน สะอาดตา เหมาะใช้งานตอนกลางวัน
- **Dark Mode**: ดุดัน สปอร์ตสไตล์หน้าปัดรถ ถนอมสายตาช่วงกลางคืน
- สลับได้ทันทีจาก TopAppBar หรือหน้าตั้งค่า โดยระบบจดจำค่าไว้ใน DataStore

### 🌐 ระบบสลับภาษา (Localization)
- รองรับ **ภาษาไทย (TH)** และ **ภาษาอังกฤษ (EN)**
- เปลี่ยนภาษาได้แบบ Runtime ผ่าน Android 13+ LocaleManager
- ระบบจดจำภาษาที่เลือกไว้แม้ปิดแอปแล้วเปิดใหม่

### 🎨 Premium Design
- ใช้ **Material Design 3** (Material You) เต็มรูปแบบ
- Color Palette แบบ **"Sporty Premium"** — สีแดง Racing Red เป็นสีหลัก
- Typography ที่อ่านง่าย เหมาะกับ Dashboard
- Micro-animations: ปุ่ม Pulse ที่หน้า Empty State, Crossfade ตอนสลับ Tab

---

## 🎯 การแก้ปัญหาและการใช้งานจริง

### ปัญหาที่แอปนี้แก้ไข
คนมีรถยนต์มักเผชิญปัญหาเหล่านี้:
1. **ลืมเปลี่ยนอะไหล่ตามระยะ** → ระบบ Predictive Alert ช่วยเตือนอัตโนมัติ
2. **ไม่รู้ว่าเดือนนี้จ่ายค่ารถไปเท่าไหร่** → Dashboard สรุปยอดรายเดือน/รายปี
3. **จำไม่ได้ว่าเปลี่ยนน้ำมันเครื่องครั้งล่าสุดเมื่อไหร่** → ประวัติการบำรุงรักษาเรียงตามวันที่
4. **บันทึกข้อมูลยุ่งยาก** → UI ที่เรียบง่าย กดจิ้มผ่าน Filter Chips ได้ในคลิกเดียว

### User Flow หลัก
```
เปิดแอป → เห็น Dashboard (ข้อมูลรถ + Alert + ค่าใช้จ่าย)
        → กด FAB "บันทึกข้อมูล" → เลือก Tab การดูแล/ค่าใช้จ่าย → บันทึก → Snackbar ยืนยัน (กด Undo ได้)
        → กลับ Dashboard → ข้อมูลอัพเดตทันที
```

---

## 🏗️ สถาปัตยกรรมและเทคโนโลยี

### MVVM Architecture Pattern
```
┌──────────────┐     ┌──────────────┐     ┌──────────────┐
│     VIEW     │────▶│  VIEWMODEL   │────▶│    MODEL     │
│  (Compose)   │◀────│ (StateFlow)  │◀────│  (Room DB)   │
└──────────────┘     └──────────────┘     └──────────────┘
   Screens &            Business           Entity, DAO,
   Navigation            Logic             Database
```

### Tech Stack

| เทคโนโลยี | การใช้งาน |
|-----------|----------|
| **Kotlin** | ภาษาหลักในการพัฒนา |
| **Jetpack Compose** | Modern UI Toolkit (Declarative UI) |
| **Room Database** | จัดเก็บข้อมูลรถ, การบำรุงรักษา, ค่าใช้จ่าย |
| **ViewModel + StateFlow** | จัดการสถานะ UI แบบ Reactive |
| **Navigation Compose** | การนำทางระหว่างหน้าจอ |
| **DataStore Preferences** | เก็บการตั้งค่า (Dark Mode, ภาษา) |
| **Coil** | โหลดและแสดงรูปภาพรถ |
| **Coroutines + Flow** | การทำงานแบบ Asynchronous |
| **Material Design 3** | ระบบดีไซน์ที่ทันสมัย |

---

## 📱 ฟีเจอร์หลัก

### หน้าจอทั้งหมด

| หน้าจอ | คำอธิบาย |
|--------|----------|
| **Dashboard** | แสดงรูปรถ, เลขไมล์, Predictive Alert, สรุปค่าใช้จ่าย, ประวัติล่าสุด |
| **Add Car** | เพิ่มรถยนต์ใหม่ พร้อมรูปจาก Gallery |
| **Add Record** | บันทึกการดูแล (Tab 1) หรือค่าใช้จ่าย (Tab 2) |
| **Settings** | สลับ Dark Mode, เปลี่ยนภาษา |
| **Empty State** | หน้าว่างพร้อม Animation เชิญชวนเพิ่มรถคันแรก |

### ฟีเจอร์เสริม UX
- ✅ **Snackbar + Undo** — บันทึกสำเร็จแล้วกด Undo ย้อนกลับได้
- ✅ **Field Validation** — ช่องกรอกข้อมูลขึ้นขอบแดงพร้อมข้อความเตือนเมื่อกรอกไม่ครบ
- ✅ **Crossfade Animation** — สลับ Tab อย่างสมูธ
- ✅ **Pulse Button Animation** — ปุ่มเพิ่มรถคันแรกที่ดูน่ากด
- ✅ **Sticky Bottom Button** — ปุ่ม Save ลอยติดล่างจอ ไม่ต้องเลื่อนหา
- ✅ **DatePicker** — เลือกวันที่จาก Calendar ของ Material 3
- ✅ **Photo Picker** — เลือกรูปรถจาก Gallery

---

## 📂 โครงสร้างโปรเจกต์

```
app/src/main/java/com/example/project_app/
├── MainActivity.kt                          # จุดเริ่มต้นแอป
├── data/local/
│   ├── entity/
│   │   ├── CarEntity.kt                     # ตารางรถยนต์
│   │   ├── MaintenanceEntity.kt             # ตารางการบำรุงรักษา
│   │   ├── ExpenseEntity.kt                 # ตารางค่าใช้จ่าย
│   │   └── SummaryEntities.kt               # Data class สำหรับสรุปยอด
│   ├── CarDao.kt                            # CRUD รถยนต์
│   ├── MaintenanceDao.kt                    # CRUD + สรุปยอดการดูแล
│   ├── ExpenseDao.kt                        # CRUD + สรุปยอดค่าใช้จ่าย
│   ├── CarDatabase.kt                       # Room Database singleton
│   └── SettingsDataStore.kt                 # DataStore สำหรับตั้งค่า
├── ui/
│   ├── navigation/
│   │   ├── AppNavigation.kt                 # NavHost + Routes
│   │   └── AppViewModelFactory.kt           # Factory สำหรับสร้าง ViewModel
│   ├── screens/
│   │   ├── home/
│   │   │   ├── DashboardScreen.kt           # หน้า Dashboard + Empty State
│   │   │   └── DashboardViewModel.kt        # ลอจิก Dashboard + Predictive Alert
│   │   ├── add_car/
│   │   │   ├── AddCarScreen.kt              # หน้าเพิ่มรถ + Validation
│   │   │   └── CarViewModel.kt              # ลอจิกเพิ่มรถ
│   │   ├── add_record/
│   │   │   ├── AddRecordScreen.kt           # หน้าบันทึก + Crossfade + Undo
│   │   │   └── AddRecordViewModel.kt        # ลอจิกบันทึก + Undo
│   │   └── settings/
│   │       ├── SettingsScreen.kt            # หน้าตั้งค่า
│   │       └── SettingsViewModel.kt         # ลอจิก Dark Mode + ภาษา
│   └── theme/
│       ├── Color.kt                         # Sporty Premium Color Palette
│       ├── Theme.kt                         # Light/Dark Theme + Override
│       └── Type.kt                          # Typography
└── res/
    ├── values/strings.xml                   # ภาษาอังกฤษ (Default)
    ├── values-th/strings.xml                # ภาษาไทย
    └── xml/locales_config.xml               # รายการภาษาที่รองรับ
```

---

## 🧠 สิ่งที่ได้เรียนรู้

### 1. Kotlin & Jetpack Compose
- เขียน UI แบบ Declarative ด้วย `@Composable` functions
- ใช้ `State`, `StateFlow`, `collectAsState` จัดการสถานะ UI แบบ Reactive
- ประยุกต์ใช้ Material Design 3 Components: `Card`, `FilterChip`, `TopAppBar`, `FAB`, `Scaffold`

### 2. MVVM Architecture
- แยก Business Logic ออกจาก UI อย่างชัดเจน
- ViewModel ทำหน้าที่เป็นตัวกลางระหว่าง View (Compose) กับ Model (Room)
- ใช้ `viewModelScope.launch` สำหรับ Coroutines ที่ผูกกับ Lifecycle

### 3. Room Database
- ออกแบบ Entity 3 ตาราง พร้อม `@ForeignKey` และ `@Index`
- เขียน DAO ด้วย `@Query` ที่มี SQL ซับซ้อน (GROUP BY, strftime, SUM)
- ใช้ `Flow` เพื่อให้ข้อมูลอัพเดต UI แบบ Real-time

### 4. DataStore Preferences
- เก็บการตั้งค่า Dark Mode และภาษาแบบ Persistent
- ใช้ `Flow` อ่านค่าแบบ Reactive
- เปรียบเทียบกับ SharedPreferences แล้วเข้าใจข้อดี: Type-safe, Coroutine-based

### 5. Localization & Runtime Language Switching
- สร้าง Resource files แยกภาษา (`values/` สำหรับ EN, `values-th/` สำหรับ TH)
- ใช้ Android 13+ `LocaleManager` เปลี่ยนภาษาแบบ Per-App
- เรียนรู้การทำ `locales_config.xml` สำหรับระบบ

---

## 🚀 ฟีเจอร์ในอนาคต

| ฟีเจอร์ | คำอธิบาย |
|---------|----------|
| 📍 **Trip Log** | บันทึกเส้นทางการเดินทาง พร้อมคำนวณ km/ลิตร |
| ☁️ **Cloud Sync** | สำรองข้อมูลขึ้น Cloud (Firebase/Supabase) |
| 📊 **กราฟสถิติ** | แสดงกราฟค่าใช้จ่ายรายเดือน/รายปี แบบ Interactive |
| 🔔 **Push Notification** | แจ้งเตือนเมื่อถึงระยะเปลี่ยนอะไหล่ |
| 🚘 **รองรับหลายคัน** | เลือกดูข้อมูลรถแต่ละคันจาก Drawer/Bottom Nav |
| 📸 **OCR ใบเสร็จ** | ถ่ายรูปใบเสร็จแล้วดึงข้อมูลอัตโนมัติ |

---

## ⚙️ วิธีติดตั้งและรันโปรเจกต์

### ข้อกำหนดเบื้องต้น
- Android Studio Ladybug (2024.2.1) หรือใหม่กว่า
- Kotlin 2.0+
- Android SDK API 36
- JDK 11+

### ขั้นตอน
1. Clone Repository
   ```bash
   git clone https://github.com/your-username/CP213_176_LearnAndroid.git
   ```
2. เปิดโฟลเดอร์ `Project_App` ด้วย Android Studio
3. รอ Gradle Sync เสร็จ
4. เลือก Device/Emulator ที่รัน API 36
5. กด **Run** ▶️

---

## 👨‍💻 ผู้พัฒนา

- **รหัสวิชา**: CP213
- **รหัสกลุ่ม**: 176

---

<p align="center">
  <b>🏎️ Built with ❤️ using Kotlin & Jetpack Compose</b>
</p>
