<p align="center">
  <img src="https://img.icons8.com/color/120/car--v1.png" alt="iDrive Logo"/>
</p>

<h1 align="center">iDrive — Your Smart Car Companion</h1>

<p align="center">
  <strong>แอปพลิเคชัน Android สำหรับบริหารจัดการรถยนต์ส่วนตัวอย่างครบวงจร</strong><br/>
  ตั้งแต่การบันทึกค่าใช้จ่าย การดูแลรักษา ไปจนถึงระบบแจ้งเตือนพยากรณ์การเข้าศูนย์บริการล่วงหน้า
</p>

---

## เกี่ยวกับโปรเจ็ค

**iDrive** คือแอปพลิเคชัน Android ที่ช่วยให้ผู้ใช้สามารถบริหารจัดการรถยนต์ส่วนตัวได้อย่างมีประสิทธิภาพ ไม่ว่าจะเป็นการบันทึกข้อมูลการบำรุงรักษา (เปลี่ยนน้ำมันเครื่อง, ผ้าเบรก, ยาง, แบตเตอรี่) การบันทึกค่าใช้จ่ายทั่วไป (ค่าน้ำมัน, ค่าทางด่วน, ค่าที่จอดรถ) รวมถึง **ระบบ Predictive Alert** ที่จะวิเคราะห์เลขไมล์สะสมเพื่อแจ้งเตือนผู้ใช้ล่วงหน้าว่าถึงเวลาเข้าศูนย์บริการ

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
- ใช้ **Material 3 DatePicker** สำหรับเลือกวันที่

### บันทึกค่าใช้จ่าย (Expense Tracking)
- เลือกหมวดหมู่ค่าใช้จ่าย: ค่าน้ำมัน, ค่าทางด่วน, ค่าที่จอดรถ, อื่นๆ
- บันทึกจำนวนเงิน, วันที่, และบันทึกเพิ่มเติม
- รองรับ **Undo (เลิกทำ)** หลังบันทึกผ่าน Snackbar

### Dashboard อัจฉริยะ
- แสดง **Hero Image Card** ของรถพร้อมชื่อยี่ห้อ/รุ่น และเลขไมล์สะสม
- สรุปค่าใช้จ่ายรายเดือน/รายปี (แสดงเป็นสกุลเงินบาท ฿)
- แสดง 5 รายการธุรกรรมล่าสุด (ทั้ง Maintenance + Expense)
- สลับดูรถคันอื่นได้ง่ายผ่านระบบ Garage

### Predictive Alert — ระบบแจ้งเตือนพยากรณ์ *(Wow Factor!)*
- วิเคราะห์ระยะทางที่วิ่งตั้งแต่เปลี่ยนน้ำมันเครื่องครั้งล่าสุด
- แจ้งเตือน **3 ระดับ** ด้วยระบบสี:
  - 🟢 **GOOD** — สภาพดี พร้อมลุยทุกเส้นทาง
  - 🟡 **WARNING** — ใกล้ถึงระยะเช็ค/เปลี่ยนถ่ายน้ำมันแล้ว (> 8,000 km)
  - 🔴 **DANGER** — เกินกำหนดระยะบำรุงรักษาแล้ว (> 10,000 km)
- ช่วยให้ผู้ใช้ไม่พลาดรอบเช็คระยะ ลดความเสี่ยงเครื่องยนต์เสียหาย

### ตั้งค่าแอปพลิเคชัน (Settings)
- 🌙 **Dark Mode** — สลับธีมสว่าง/มืดได้แบบ Real-time (บันทึกค่าผ่าน DataStore)
- 🌐 **Multi-language** — รองรับ 2 ภาษา: ไทย 🇹🇭 และ English 🇺🇸
- ℹ️ แสดงข้อมูลเวอร์ชันแอป

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
| **Build Tool** | Gradle Kotlin DSL (KTS) |
| **Annotation Processing** | KSP (Kotlin Symbol Processing) |
| **Min SDK** | API 36 |
| **Target SDK** | API 36 |

---

## Project Architecture

โปรเจ็คนี้ใช้สถาปัตยกรรม **MVVM (Model-View-ViewModel)** แยก Layer อย่างชัดเจน:

```
📂 com.example.project_app
├── 📄 MainActivity.kt                    # Entry Point
│
├── 📂 data/                               # ── Data Layer ──
│   └── 📂 local/
│       ├── 📄 CarDatabase.kt              # Room Database (Singleton)
│       ├── 📄 CarDao.kt                   # DAO — CRUD รถยนต์
│       ├── 📄 MaintenanceDao.kt           # DAO — CRUD การบำรุงรักษา
│       ├── 📄 ExpenseDao.kt               # DAO — CRUD ค่าใช้จ่าย
│       ├── 📄 SettingsDataStore.kt         # DataStore — ค่าตั้งค่าแอป
│       └── 📂 entity/
│           ├── 📄 CarEntity.kt            # Entity — ข้อมูลรถยนต์
│           ├── 📄 MaintenanceEntity.kt    # Entity — ข้อมูลการบำรุงรักษา
│           ├── 📄 ExpenseEntity.kt        # Entity — ข้อมูลค่าใช้จ่าย
│           └── 📄 SummaryEntities.kt      # Data Class — สรุปรายเดือน/รายปี
│
└── 📂 ui/                                 # ── Presentation Layer ──
    ├── 📂 navigation/
    │   ├── 📄 AppNavigation.kt            # NavHost + Route ทั้งหมด
    │   └── 📄 AppViewModelFactory.kt      # ViewModel Factory
    │
    ├── 📂 screens/
    │   ├── 📂 home/
    │   │   ├── 📄 MainHomeScreen.kt       # หน้าแรก — Landing Page
    │   │   ├── 📄 DashboardScreen.kt      # หน้า Dashboard + Garage
    │   │   └── 📄 DashboardViewModel.kt   # ViewModel — Logic Dashboard
    │   │
    │   ├── 📂 add_car/
    │   │   ├── 📄 AddCarScreen.kt         # หน้าเพิ่มรถยนต์ใหม่
    │   │   └── 📄 CarViewModel.kt         # ViewModel — Logic เพิ่มรถ
    │   │
    │   ├── 📂 add_record/
    │   │   ├── 📄 AddRecordScreen.kt      # หน้าบันทึกข้อมูล (Maintenance/Expense)
    │   │   └── 📄 AddRecordViewModel.kt   # ViewModel — Logic บันทึกข้อมูล
    │   │
    │   ├── 📂 settings/
    │   │   ├── 📄 SettingsScreen.kt       # หน้าตั้งค่า
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
| 🔴 | Racing Red | `#E63946` | Primary — ให้ความรู้สึกมีพลัง ทันสมัย |
| 🔵 | Carbon Navy | `#1D3557` | Secondary — ความหรูหรา เชื่อถือได้ |
| 🟡 | Engine Amber | `#FFB703` | Warning — สีส้มหน้าปัดรถ |
| 🟢 | Safe Green | `#2A9D8F` | Success — บ่งบอกความปลอดภัย |
| ⚪ | Pearl White | `#F8F9FA` | Background (Light Mode) |
| ⚫ | Matte Carbon | `#121212` | Background (Dark Mode) |

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
  ┌─────┴──────────┐
  │                │
  ▼                ▼
┌─────────────────┐  ┌─────────────────┐
│  maintenances    │  │    expenses      │
├─────────────────┤  ├─────────────────┤
│ id (PK, Auto)   │  │ id (PK, Auto)   │
│ carId (FK)      │  │ carId (FK)      │
│ type (String)   │  │ type (String)   │
│ date (Long)     │  │ date (Long)     │
│ mileage (Int)   │  │ amount (Double) │
│ cost (Double)   │  │ notes (String?) │
│ notes (String?) │  └─────────────────┘
└─────────────────┘
```

> ใช้ **Foreign Key** ผูกความสัมพันธ์ `carId` กับ `cars.id` และตั้ง **ON DELETE CASCADE** เพื่อให้ข้อมูลที่เกี่ยวข้องถูกลบอัตโนมัติเมื่อลบรถ

---

## วิธีติดตั้งและรันโปรเจ็ค

### Prerequisites
- **Android Studio** Ladybug (2024.2+) หรือใหม่กว่า
- **JDK 11** ขึ้นไป
- **Android SDK** API Level 36

### ขั้นตอน

```bash
# 1. Clone Repository
git clone https://github.com/<your-username>/CP213_176_LearnAndroid.git

# 2. เปิดโฟลเดอร์ Project_App ด้วย Android Studio

# 3. Sync Gradle แล้วรอให้ดาวน์โหลด Dependencies เสร็จ

# 4. เลือก Device/Emulator (API 36+) แล้วกด Run
```

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
└───────────────┘│││                     │ - Expenses   │
                 │││                     │ - Tx History │
                 │││                     │              │
                 │││                     │ [+ Add Record]│
                 │││                     └──────┬───────┘
                 │││                            │
                 │││                    ┌───────▼───────┐
                 │││                    │ Add Record    │
                 │││                    │               │
                 │││                    │ Tab: Maint.   │
                 │││                    │ Tab: Expense  │
                 │││                    │               │
                 │││                    │ [Date Picker] │
                 │││                    │ [FilterChips] │
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
2. **Room Database** — ออกแบบ Schema, Entity, DAO, Foreign Key, CASCADE Delete
3. **MVVM Architecture** — แยก Business Logic ออกจาก UI ด้วย ViewModel + StateFlow
4. **Kotlin Coroutines & Flow** — จัดการ Async Operations และ Reactive Data Stream
5. **Navigation Compose** — จัดการ Route และ Argument Passing ระหว่างหน้าจอ
6. **DataStore Preferences** — บันทึกค่าตั้งค่าอย่างปลอดภัย (แทน SharedPreferences)
7. **Material Design 3** — ใช้ Theme System, Dynamic Color Scheme, FilterChip, DatePicker
8. **Localization (i18n)** — รองรับหลายภาษาด้วย `values/strings.xml` และ `values-th/strings.xml`
9. **Coil** — โหลดรูปภาพแบบ Async ใน Compose
10. **Photo Picker API** — เลือกรูปภาพจากอุปกรณ์อย่างปลอดภัย พร้อม Persistable URI Permission
