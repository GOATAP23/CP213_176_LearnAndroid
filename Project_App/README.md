<h1 align="center">iDrive</h1>

<p align="center">
  <strong>แอปพลิเคชัน Android สำหรับบริหารจัดการรถยนต์ส่วนตัวอย่างครบวงจร</strong><br/>
  ตั้งแต่การบันทึกค่าใช้จ่าย การดูแลรักษา ระบบแจ้งเตือนพยากรณ์ การจับระยะทางอัตโนมัติ ไปจนถึงกราฟวิเคราะห์ค่าใช้จ่ายและการส่งออกข้อมูล
</p>

---

## เกี่ยวกับโปรเจ็ค

**iDrive** คือแอปพลิเคชัน Android ที่ช่วยให้ผู้ใช้สามารถบริหารจัดการรถยนต์ส่วนตัวได้อย่างมีประสิทธิภาพ ไม่ว่าจะเป็นการบันทึกข้อมูลการบำรุงรักษา (เปลี่ยนน้ำมันเครื่อง, ผ้าเบรก, ยาง, แบตเตอรี่) การบันทึกค่าใช้จ่ายทั่วไป (ค่าน้ำมัน, ค่าทางด่วน, ค่าที่จอดรถ) รวมถึง **ระบบ Predictive Alert** ที่จะวิเคราะห์เลขไมล์สะสมและจำนวนวันเพื่อแจ้งเตือนผู้ใช้ล่วงหน้าว่าถึงเวลาเข้าศูนย์บริการ

แอปรองรับ **Activity Recognition API** สำหรับจับระยะทางอัตโนมัติ, **Notification ผ่าน WorkManager**, **กราฟสรุปค่าใช้จ่าย**, **ปฏิทินดูแลรักษา**, **อัตราสิ้นเปลืองน้ำมัน (km/L)**, และ **ส่งออกข้อมูลเป็น CSV**

โดยแอปนี้ถูกพัฒนาด้วย **Kotlin** และ **Jetpack Compose** ตามแนวทาง Modern Android Development อย่างเต็มรูปแบบ — ไม่ใช้ XML Layout เลยแม้แต่หน้าเดียว

---

## Features

### การจัดการรถยนต์ (Car Management)
- เพิ่มรถยนต์ได้หลายคัน พร้อมข้อมูลยี่ห้อ, รุ่น, ปีจดทะเบียน, เลขไมล์ปัจจุบัน
- อัปโหลดรูปภาพรถจากอุปกรณ์ผ่าน **Photo Picker API** หรือวาง URL รูปภาพ
- ระบบ **Garage View** แสดงรายการรถทั้งหมดในรูปแบบ Card พร้อมรูปภาพ Hero
- ลบรถยนต์ (พร้อมลบข้อมูลที่เกี่ยวข้องทั้งหมดอัตโนมัติ ด้วย **CASCADE Delete**)

### บันทึกการดูแลรักษา (Maintenance Tracking)
- เลือกประเภทการบำรุงรักษาผ่าน **FilterChip**: น้ำมันเครื่อง, ผ้าเบรก, ยาง, แบตเตอรี่, อื่นๆ
- บันทึกเลขไมล์ ณ วันที่เข้าเช็ค, ราคาค่าบริการ, และวันที่
- ใช้ **Material 3 DatePicker** สำหรับเลือกวันที่ (ไม่อนุญาตเลือกวันในอนาคต)
- แนบรูปภาพใบเสร็จ/สภาพรถได้ผ่าน **Photo Picker**

### บันทึกค่าใช้จ่าย (Expense Tracking)
- เลือกหมวดหมู่ค่าใช้จ่าย: ค่าน้ำมัน, ค่าทางด่วน, ค่าที่จอดรถ, อื่นๆ
- บันทึกจำนวนเงิน, วันที่, และบันทึกเพิ่มเติม
- **Fuel Economy** — เมื่อเลือก "ค่าน้ำมัน" สามารถกรอกจำนวนลิตรและเลขไมล์ตอนเติม เพื่อคำนวณอัตราสิ้นเปลือง (km/L)
- รองรับ **Undo (เลิกทำ)** หลังบันทึกผ่าน Snackbar

### Dashboard อัจฉริยะ
- แสดง **Hero Image Card** ของรถพร้อมชื่อยี่ห้อ/รุ่น และเลขไมล์สะสม
- สรุปค่าใช้จ่ายรายเดือน/รายปี (แสดงเป็นสกุลเงินบาท ฿)
- แสดง 5 รายการธุรกรรมล่าสุด (ทั้ง Maintenance + Expense)
- **Fuel Economy Card** — แสดงอัตราสิ้นเปลืองเฉลี่ยและล่าสุด (km/L)
- ปุ่มลัด **History / Trips / Calendar** เข้าถึงฟีเจอร์ใหม่ได้ทันที
- สลับดูรถคันอื่นได้ง่ายผ่านระบบ Garage

### Predictive Alert — ระบบแจ้งเตือนพยากรณ์ *(Wow Factor!)*
- วิเคราะห์ระยะทางที่วิ่ง + จำนวนวันตั้งแต่เปลี่ยนน้ำมันเครื่องครั้งล่าสุด
- รองรับ **รอบ Maintenance แบบ Custom** (ตั้งค่าได้ใน Settings)
- แจ้งเตือน **3 ระดับ** ด้วยระบบสี:
  - **GOOD** — สภาพดี พร้อมลุยทุกเส้นทาง
  - **WARNING** — ใกล้ถึงระยะเช็ค/เปลี่ยนถ่ายน้ำมันแล้ว
  - **DANGER** — เกินกำหนดระยะบำรุงรักษาแล้ว
- **Push Notification** — แจ้งเตือนอัตโนมัติทุก 24 ชม. ผ่าน **WorkManager**

### ประวัติ (History Screen)
- ดูประวัติ **Maintenance + Expense** ทั้งหมดในหน้าจอเดียว
- **กราฟแท่ง** สรุปค่าใช้จ่าย 6 เดือนย้อนหลัง (แยกสี Maintenance/Expense)
- **ค้นหา** รายการจาก notes หรือประเภท
- **กรอง** ตามประเภท (FilterChip)
- กดเพื่อ **แก้ไข** หรือ **ลบ** รายการได้

### บันทึกการเดินทางอัตโนมัติ (Trip Tracking)
- ใช้ **Activity Recognition API** ตรวจจับเมื่อผู้ใช้อยู่ในรถ (`IN_VEHICLE`)
- จับ **GPS Location** ด้วย Foreground Service เพื่อคำนวณระยะทาง
- แสดงประวัติ Trips พร้อมระยะทาง, ระยะเวลา, วันที่
- อัปเดตเลขไมล์ของรถอัตโนมัติเมื่อจบ Trip

### ปฏิทิน (Calendar View)
- ปฏิทินแบบ Monthly Navigation พร้อม **จุดสี** บนวันที่มี event
- 🟢 จุดสำหรับ Maintenance / 🔵 จุดสำหรับ Expense
- ไฮไลท์วันปัจจุบัน, แสดง Legend สัญลักษณ์

### ตั้งค่าแอปพลิเคชัน (Settings)
- **Dark Mode** — สลับธีมสว่าง/มืดได้แบบ Real-time (บันทึกค่าผ่าน DataStore)
- **Multi-language** — รองรับ 2 ภาษา: ไทย 🇹🇭 และ English 🇺🇸
- **รอบ Maintenance** — ตั้งค่ารอบเปลี่ยนน้ำมันเครื่องได้ (กม. + วัน)
- **Auto-Track** — เปิด/ปิดการจับระยะทางอัตโนมัติ
- **Export Data** — ส่งออกข้อมูลทั้งหมดเป็นไฟล์ CSV
- แสดงข้อมูลเวอร์ชันแอป

### แชร์ข้อมูล (Share)
- สร้าง Text Summary ของประวัติรถพร้อมค่าใช้จ่ายรวม
- แชร์ผ่าน LINE, Messenger, Email หรือแอปอื่นๆ ได้ทันที

### Tutorial ในแอป
- มีหน้าสอนการใช้งาน 3 ขั้นตอน ให้ผู้ใช้ใหม่เรียนรู้ได้ง่าย ไม่ต้องงม

---

## Tech Stack

| เทคโนโลยี | รายละเอียด |
|---|---|
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose (Material 3 / Material Design 3) |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Local Database** | Room Database (SQLite) |
| **Preferences** | DataStore Preferences |
| **Image Loading** | Coil Compose |
| **Photo Picker** | AndroidX Activity Result (PickVisualMedia) |
| **Navigation** | Navigation Compose |
| **Async** | Kotlin Coroutines + Flow |
| **Background Worker** | WorkManager (Periodic Maintenance Check) |
| **Activity Recognition** | Google Play Services Location (Activity Recognition Transition API) |
| **Charts** | Compose Canvas (Custom Bar Chart) |
| **Build Tool** | Gradle Kotlin DSL (KTS) |
| **Annotation Processing** | KSP (Kotlin Symbol Processing) |
| **Min SDK** | API 36 |
| **Target SDK** | API 36 |

---

## Project Architecture

โปรเจ็คนี้ใช้สถาปัตยกรรม **MVVM (Model-View-ViewModel)** แยก Layer อย่างชัดเจน:

```
📂 com.example.project_app
├── 📄 MainActivity.kt                    # Entry Point + WorkManager Setup
│
├── 📂 data/                               # ── Data Layer ──
│   ├── 📂 local/
│   │   ├── 📄 CarDatabase.kt              # Room Database v2 (Singleton)
│   │   ├── 📄 CarDao.kt                   # DAO — CRUD รถยนต์
│   │   ├── 📄 MaintenanceDao.kt           # DAO — CRUD การบำรุงรักษา
│   │   ├── 📄 ExpenseDao.kt               # DAO — CRUD ค่าใช้จ่าย
│   │   ├── 📄 TripDao.kt                  # DAO — CRUD การเดินทาง
│   │   ├── 📄 SettingsDataStore.kt         # DataStore — ค่าตั้งค่าแอป + Intervals
│   │   └── 📂 entity/
│   │       ├── 📄 CarEntity.kt            # Entity — ข้อมูลรถยนต์
│   │       ├── 📄 MaintenanceEntity.kt    # Entity — การบำรุงรักษา (+imagePath)
│   │       ├── 📄 ExpenseEntity.kt        # Entity — ค่าใช้จ่าย (+liters, mileageAtFill, imagePath)
│   │       ├── 📄 TripEntity.kt           # Entity — การเดินทาง (NEW)
│   │       └── 📄 SummaryEntities.kt      # Data Class — สรุปรายเดือน/รายปี
│   │
│   ├── 📂 service/
│   │   ├── 📄 ActivityRecognitionReceiver.kt  # BroadcastReceiver — ตรวจจับ IN_VEHICLE/STILL
│   │   └── 📄 TripTrackingService.kt          # Foreground Service — จับ GPS ระหว่างขับ
│   │
│   ├── 📂 worker/
│   │   └── 📄 MaintenanceCheckWorker.kt   # WorkManager — ตรวจสอบทุก 24 ชม. + Notification
│   │
│   └── 📂 export/
│       ├── 📄 CsvExporter.kt              # ส่งออกข้อมูลเป็น CSV
│       └── 📄 ShareHelper.kt              # สร้าง Text Summary + แชร์
│
└── 📂 ui/                                 # ── Presentation Layer ──
    ├── 📂 navigation/
    │   ├── 📄 AppNavigation.kt            # NavHost + Route ทั้งหมด (12 routes)
    │   └── 📄 AppViewModelFactory.kt      # ViewModel Factory (7 ViewModels)
    │
    ├── 📂 screens/
    │   ├── 📂 home/
    │   │   ├── 📄 MainHomeScreen.kt       # หน้าแรก — Landing Page
    │   │   ├── 📄 DashboardScreen.kt      # Dashboard + Garage + Fuel Economy
    │   │   └── 📄 DashboardViewModel.kt   # ViewModel — Logic Dashboard + Alert + Fuel
    │   │
    │   ├── 📂 add_car/
    │   │   ├── 📄 AddCarScreen.kt         # เพิ่ม/แก้ไขรถยนต์
    │   │   └── 📄 CarViewModel.kt         # ViewModel — Logic เพิ่มรถ
    │   │
    │   ├── 📂 add_record/
    │   │   ├── 📄 AddRecordScreen.kt      # บันทึก/แก้ไขข้อมูล + Fuel + Photo
    │   │   └── 📄 AddRecordViewModel.kt   # ViewModel — Logic บันทึก + Edit Mode
    │   │
    │   ├── 📂 history/
    │   │   ├── 📄 HistoryScreen.kt        # ประวัติทั้งหมด + กราฟ + ค้นหา/กรอง (NEW)
    │   │   └── 📄 HistoryViewModel.kt     # ViewModel — History Logic (NEW)
    │   │
    │   ├── 📂 trips/
    │   │   ├── 📄 TripScreen.kt           # ประวัติการเดินทาง (NEW)
    │   │   └── 📄 TripViewModel.kt        # ViewModel — Trip Logic (NEW)
    │   │
    │   ├── 📂 calendar/
    │   │   ├── 📄 CalendarScreen.kt       # ปฏิทินดูแลรักษา (NEW)
    │   │   └── 📄 CalendarViewModel.kt    # ViewModel — Calendar Logic (NEW)
    │   │
    │   ├── 📂 settings/
    │   │   ├── 📄 SettingsScreen.kt       # ตั้งค่า + Intervals + Export
    │   │   └── 📄 SettingsViewModel.kt    # ViewModel — Logic ตั้งค่า
    │   │
    │   └── 📂 tutorial/
    │       └── 📄 TutorialScreen.kt       # หน้าสอนการใช้งาน
    │
    └── 📂 theme/
        ├── 📄 Color.kt                    # 🎨 Color Palette: "Sporty Premium"
        ├── 📄 Theme.kt                    # Material 3 Theme (Light/Dark)
        └── 📄 Type.kt                     # Typography
```

---

## Design Concept — *"Sporty Premium"*

ธีมสีของแอปได้รับแรงบันดาลใจจากรถสปอร์ตระดับพรีเมียม:

| สี | ชื่อ | Hex | การใช้งาน |
|---|---|---|---|
| Racing Red | `#E63946` | Primary — ให้ความรู้สึกมีพลัง ทันสมัย |
| Carbon Navy | `#1D3557` | Secondary — ความหรูหรา เชื่อถือได้ |
| Engine Amber | `#FFB703` | Warning — สีส้มหน้าปัดรถ |
| Safe Green | `#2A9D8F` | Success — บ่งบอกความปลอดภัย |
| Pearl White | `#F8F9FA` | Background (Light Mode) |
| Matte Carbon | `#121212` | Background (Dark Mode) |

---

## 📱 Database Schema (ERD)

```
┌─────────────────────────┐
│        cars              │
├─────────────────────────┤
│ id (PK, Auto)           │
│ brand (String)           │
│ model (String)           │
│ year (Int)               │
│ currentMileage (Int)     │
│ imagePath (String?)      │
└───────┬─────────────────┘
        │ 1:N (CASCADE)
        │
  ┌─────┼──────────┬──────────────┐
  │     │          │              │
  ▼     │          ▼              ▼
┌─────────────────┐  ┌──────────────────┐  ┌──────────────┐
│  maintenances    │  │    expenses       │  │    trips      │
├─────────────────┤  ├──────────────────┤  ├──────────────┤
│ id (PK, Auto)   │  │ id (PK, Auto)    │  │ id (PK, Auto)│
│ carId (FK)      │  │ carId (FK)       │  │ carId (FK)   │
│ type (String)   │  │ type (String)    │  │ startTime    │
│ date (Long)     │  │ date (Long)      │  │ endTime      │
│ mileage (Int)   │  │ amount (Double)  │  │ distanceKm   │
│ cost (Double)   │  │ notes (String?)  │  │ isActive     │
│ notes (String?) │  │ liters (Double?) │  └──────────────┘
│ imagePath       │  │ mileageAtFill    │
│  (String?)      │  │  (Int?)          │
└─────────────────┘  │ imagePath        │
                     │  (String?)       │
                     └──────────────────┘
```

> ใช้ **Foreign Key** ผูกความสัมพันธ์ `carId` กับ `cars.id` และตั้ง **ON DELETE CASCADE** เพื่อให้ข้อมูลที่เกี่ยวข้องถูกลบอัตโนมัติเมื่อลบรถ

---

## วิธีติดตั้งและรันโปรเจ็ค

### Prerequisites
- **Android Studio** Ladybug (2024.2+) หรือใหม่กว่า
- **JDK 11** ขึ้นไป
- **Android SDK** API Level 36
- **Google Play Services** (สำหรับ Activity Recognition — ใช้ได้บน Physical Device เท่านั้น)

### ขั้นตอน

```bash
# 1. Clone Repository
git clone https://github.com/<your-username>/CP213_176_LearnAndroid.git

# 2. เปิดโฟลเดอร์ Project_App ด้วย Android Studio

# 3. Sync Gradle แล้วรอให้ดาวน์โหลด Dependencies เสร็จ

# 4. เลือก Device/Emulator (API 36+) แล้วกด Run
```

### Permissions ที่แอปต้องใช้
| Permission | วัตถุประสงค์ |
|---|---|
| `INTERNET` | โหลดรูปภาพจาก URL |
| `POST_NOTIFICATIONS` | ส่ง Push Notification แจ้งเตือนบำรุงรักษา |
| `ACTIVITY_RECOGNITION` | ตรวจจับการขับรถ (IN_VEHICLE) |
| `ACCESS_FINE_LOCATION` | จับ GPS ระหว่างขับเพื่อคำนวณระยะทาง |
| `ACCESS_COARSE_LOCATION` | Location Approximate |
| `FOREGROUND_SERVICE` | รัน Trip Tracking Service |
| `FOREGROUND_SERVICE_LOCATION` | ใช้ GPS ใน Foreground Service |

---

## Wireframe / App Flow

```
┌───────────────┐
│  Home Screen  │  ← Landing Page (iDrive Branding)
│               │
│ ┌───────────┐ │
│ │  Start    │─┼──►┌──────────────┐
│ └───────────┘ │   │ Garage View  │  ← เลือกรถ (ถ้ามีหลายคัน)
│ ┌───────────┐ │   │              │
│ │ Tutorial  │─┼─┐ │  [Car 1]     │───►┌──────────────┐
│ └───────────┘ │ │ │  [Car 2]     │    │  Dashboard   │
│ ┌───────────┐ │ │ │  [+ Add]     │    │              │
│ │ Settings  │─┼┐│ └──────────────┘    │ - Hero Image │
│ └───────────┘ │││                     │ - Alert Card │
└───────────────┘│││                     │ - Fuel Econ. │
                 │││                     │ - Quick Nav  │─┬──►┌──────────┐
                 │││                     │ - Expenses   │ │   │ History  │ ← กราฟ + ค้นหา + กรอง
                 │││                     │ - Tx History │ │   └──────────┘
                 │││                     │              │ ├──►┌──────────┐
                 │││                     │ [+ Add Record]│ │   │  Trips   │ ← auto-tracked
                 │││                     └──────┬───────┘ │   └──────────┘
                 │││                            │         └──►┌──────────┐
                 │││                    ┌───────▼───────┐     │ Calendar │ ← ปฏิทิน
                 │││                    │ Add/Edit Record│     └──────────┘
                 │││                    │               │
                 │││                    │ Tab: Maint.   │
                 │││                    │ Tab: Expense  │
                 │││                    │ [Fuel Fields] │
                 │││                    │ [Photo Pick]  │
                 │││                    │ [Save Button] │
                 │││                    └───────────────┘
                 ││└►┌──────────────┐
                 ││  │  Tutorial    │  ← 3 ขั้นตอนการใช้งาน
                 ││  └──────────────┘
                 │└─►┌──────────────┐
                 │   │  Settings    │
                 │   │              │
                 │   │ 🌙 Dark Mode │
                 │   │ 🌐 Language  │
                 │   │ 🔧 Intervals│
                 │   │ 🚗 AutoTrack│
                 │   │ 💾 Export CSV│
                 │   │ ℹ️ Version   │
                 │   └──────────────┘
                 │
                 └──►┌──────────────┐
                     │  Add Car     │  ← เพิ่มรถใหม่
                     │              │
                     │ [Photo/URL]  │
                     │ [Brand]      │
                     │ [Model]      │
                     │ [Year]       │
                     │ [Mileage]    │
                     │ [Save]       │
                     └──────────────┘
```

---

## สิ่งที่ได้เรียนรู้จากโปรเจ็คนี้

1. **Jetpack Compose** — สร้าง UI แบบ Declarative โดยไม่ใช้ XML Layout ทั้งโปรเจ็ค
2. **Room Database** — ออกแบบ Schema, Entity, DAO, Foreign Key, CASCADE Delete, Migration
3. **MVVM Architecture** — แยก Business Logic ออกจาก UI ด้วย ViewModel + StateFlow
4. **Kotlin Coroutines & Flow** — จัดการ Async Operations และ Reactive Data Stream
5. **Navigation Compose** — จัดการ Route และ Argument Passing ระหว่างหน้าจอ
6. **DataStore Preferences** — บันทึกค่าตั้งค่าอย่างปลอดภัย (แทน SharedPreferences)
7. **Material Design 3** — ใช้ Theme System, Dynamic Color Scheme, FilterChip, DatePicker
8. **Localization (i18n)** — รองรับหลายภาษาด้วย `values/strings.xml` และ `values-th/strings.xml`
9. **Coil** — โหลดรูปภาพแบบ Async ใน Compose
10. **Photo Picker API** — เลือกรูปภาพจากอุปกรณ์อย่างปลอดภัย พร้อม Persistable URI Permission
11. **WorkManager** — ตรวจสอบ Maintenance ทุก 24 ชม. + ส่ง Push Notification
12. **Activity Recognition API** — ตรวจจับ IN_VEHICLE / STILL จาก Google Play Services
13. **Foreground Service** — จับ GPS Location ระหว่างขับรถด้วย FusedLocationProvider
14. **Canvas Drawing** — วาดกราฟแท่ง (Bar Chart) ด้วย Compose Canvas
15. **CSV Export** — สร้างไฟล์ CSV ด้วย Intent.ACTION_CREATE_DOCUMENT
16. **Intent Sharing** — แชร์ข้อมูลรถผ่าน Share Sheet (LINE, Messenger, Email ฯลฯ)
