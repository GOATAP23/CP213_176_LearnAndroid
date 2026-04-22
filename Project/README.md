<h1 align="center">iDrive</h1>

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![MVVM](https://img.shields.io/badge/Architecture-MVVM-orange?style=for-the-badge)
![Room Database](https://img.shields.io/badge/Room-Database-blue?style=for-the-badge)
![Material 3](https://img.shields.io/badge/Material_3-EADDFF?style=for-the-badge&logo=materialdesign&logoColor=black)

**แอปพลิเคชัน Android สำหรับบริหารจัดการรถยนต์ส่วนตัวอย่างครบวงจร**
ตั้งแต่การบันทึกค่าใช้จ่าย การดูแลรักษา ระบบแจ้งเตือนพยากรณ์ การจับระยะทางอัตโนมัติ ไปจนถึงกราฟวิเคราะห์ค่าใช้จ่ายและการส่งออกข้อมูล

---

## เกี่ยวกับโปรเจกต์ (About The Project)

**iDrive** คือแอปพลิเคชัน Android ที่ช่วยให้ผู้ใช้สามารถบริหารจัดการรถยนต์ส่วนตัวได้อย่างมีประสิทธิภาพ ไม่ว่าจะเป็นการบันทึกข้อมูลการบำรุงรักษา (เปลี่ยนน้ำมันเครื่อง, ผ้าเบรก, ยาง, แบตเตอรี่) การบันทึกค่าใช้จ่ายทั่วไป (ค่าน้ำมัน, ค่าทางด่วน, ค่าที่จอดรถ) รวมถึง **ระบบ Predictive Alert** ที่จะวิเคราะห์เลขไมล์สะสมและจำนวนวันเพื่อแจ้งเตือนผู้ใช้ล่วงหน้าว่าถึงเวลาเข้าศูนย์บริการ

แอปพลิเคชันนี้รองรับ **Activity Recognition API** สำหรับจับระยะทางอัตโนมัติ, **Notification ผ่าน WorkManager**, **กราฟสรุปค่าใช้จ่าย**, **ปฏิทินดูแลรักษา**, **อัตราสิ้นเปลืองน้ำมัน (km/L)**, และ **ส่งออกข้อมูลเป็น CSV**

*พัฒนาด้วย **Kotlin** และ **Jetpack Compose** ตามแนวทาง Modern Android Development อย่างเต็มรูปแบบ (ไม่ใช้ XML Layout สำหรับ UI เลยแม้แต่หน้าเดียว)*

---

## ฟีเจอร์หลัก (Key Features)

### การจัดการรถยนต์ (Car Management)
- เพิ่มรถยนต์ได้หลายคัน พร้อมข้อมูลยี่ห้อ รุ่น ปีจดทะเบียน เลขไมล์ปัจจุบัน
- อัปโหลดรูปภาพรถจากอุปกรณ์ผ่าน Photo Picker API หรือแนบ URL รูปภาพ
- ระบบ Garage View แสดงรายการรถทั้งหมดในรูปแบบ Card พร้อมรูปภาพ Hero
- ลบรถยนต์ (พร้อมลบข้อมูลที่เกี่ยวข้องทั้งหมดอัตโนมัติ ด้วย CASCADE Delete)

### บันทึกการดูแลรักษา (Maintenance Tracking)
- เลือกประเภทการบำรุงรักษา: น้ำมันเครื่อง, ผ้าเบรก, ยาง, แบตเตอรี่, อื่นๆ
- บันทึกเลขไมล์ ณ วันที่เข้าเช็ค, ราคาค่าบริการ, และวันที่
- ใช้ Material 3 DatePicker สำหรับเลือกวันที่ (ไม่อนุญาตเลือกวันในอนาคต)
- แนบรูปภาพใบเสร็จหรือสภาพรถได้ผ่าน Photo Picker

### บันทึกค่าใช้จ่าย (Expense Tracking)
- เลือกหมวดหมู่ค่าใช้จ่าย: ค่าน้ำมัน, ค่าทางด่วน, ค่าที่จอดรถ, อื่นๆ
- บันทึกจำนวนเงิน, วันที่, และบันทึกเพิ่มเติม
- **Fuel Economy:** เมื่อเลือก "ค่าน้ำมัน" สามารถกรอกจำนวนลิตรและเลขไมล์ตอนเติม เพื่อคำนวณอัตราสิ้นเปลือง (km/L)
- รองรับ Undo เลิกทำหลังบันทึกผ่าน Snackbar

### Dashboard อัจฉริยะ
- แสดง Hero Image Card ของรถพร้อมชื่อยี่ห้อ รุ่น และเลขไมล์สะสม
- สรุปค่าใช้จ่ายรายเดือนและรายปี พร้อม 5 รายการธุรกรรมล่าสุด
- Fuel Economy Card: แสดงอัตราสิ้นเปลืองเฉลี่ยและล่าสุด (km/L)
- ปุ่มลัดเข้าสู่ History, Trips และ Calendar ได้ทันที

### Predictive Alert — ระบบแจ้งเตือนพยากรณ์
- วิเคราะห์ระยะทางที่วิ่งและจำนวนวันตั้งแต่เปลี่ยนน้ำมันเครื่องครั้งล่าสุด
- แจ้งเตือน 3 ระดับด้วยระบบสี: 🟢 GOOD, 🟠 WARNING, 🔴 DANGER
- **Push Notification:** แจ้งเตือนอัตโนมัติทุก 24 ชั่วโมงผ่าน WorkManager

### การเดินทางอัตโนมัติ (Trip Tracking) & ฟีเจอร์อื่นๆ
- **Auto-Trip Tracking:** ใช้ Activity Recognition API ตรวจจับการขับรถ และ Foreground Service จับ GPS
- **History & Calendar:** ดูกราฟแท่ง ค้นหาประวัติ และดูปฏิทินบันทึกการซ่อมบำรุง
- **Settings:** รองรับ Dark Mode, Multi-language (ไทย/Eng), ตั้งค่าระยะ Maintenance แจ้งเตือน
- **Export & Share:** ส่งออกข้อมูลเป็น CSV และแชร์สรุปค่าใช้จ่ายผ่านแอปอื่น

---

## เทคโนโลยีที่ใช้ (Tech Stack)

| หมวดหมู่ | เทคโนโลยี / ไลบรารี |
|---|---|
| **ภาษาหลัก (Language)** | Kotlin |
| **UI Framework** | Jetpack Compose (Material Design 3) |
| **สถาปัตยกรรม (Architecture)** | MVVM (Model-View-ViewModel) |
| **ฐานข้อมูล (Database)** | Room Database (SQLite) |
| **การเก็บตั้งค่า (Preferences)** | DataStore Preferences |
| **จัดการรูปภาพ (Image Loading)** | Coil Compose |
| **Photo Picker** | AndroidX Activity Result (PickVisualMedia) |
| **การนำทาง (Navigation)** | Navigation Compose |
| **การทำงานแบบ Asynchronous** | Kotlin Coroutines + Flow |
| **Background Worker** | WorkManager (Periodic Maintenance Check) |
| **Activity Recognition** | Google Play Services Location |
| **วาดกราฟ (Charts)** | Compose Canvas (Custom Bar Chart) |
| **Build Tool** | Gradle Kotlin DSL (KTS) |
| **SDK Version** | Min/Target SDK: API 36 |

---

## การออกแบบ (Design Concept) — "Sporty Premium"

ธีมสีของแอปได้รับแรงบันดาลใจจากรถสปอร์ตระดับพรีเมียม:

| สี | ชื่อ (Name) | Hex Code | ความหมาย / การใช้งาน |
|---|---|---|---|
| 🔴 | Racing Red | `#E63946` | Primary — ให้ความรู้สึกมีพลัง ทันสมัย |
| 🔵 | Carbon Navy | `#1D3557` | Secondary — ความหรูหรา เชื่อถือได้ |
| 🟠 | Engine Amber| `#FFB703` | Warning — สีส้มบนหน้าปัดรถ |
| 🟢 | Safe Green | `#2A9D8F` | Success — บ่งบอกความปลอดภัย |
| ⚪ | Pearl White | `#F8F9FA` | Background (Light Mode) |
| ⚫ | Matte Carbon| `#121212` | Background (Dark Mode) |

---

## โครงสร้างโปรเจกต์ (Project Architecture)

โปรเจกต์นี้ใช้สถาปัตยกรรม **MVVM (Model-View-ViewModel)** โดยแบ่ง Layer ออกจากกันอย่างชัดเจน:

<details>
<summary><b>คลิกเพื่อดูโครงสร้างโฟลเดอร์</b></summary>

```text
com.example.project_app
├── MainActivity.kt
│
├── data/                               # -- Data Layer --
│   ├── local/
│   │   ├── CarDatabase.kt              # Room Database v2 (Singleton)
│   │   ├── CarDao.kt, MaintenanceDao.kt, ExpenseDao.kt, TripDao.kt
│   │   ├── SettingsDataStore.kt        # DataStore Preferences
│   │   └── entity/                     # Entities (Car, Maintenance, Expense, Trip)
│   │
│   ├── service/
│   │   ├── ActivityRecognitionReceiver.kt
│   │   └── TripTrackingService.kt      # Foreground Service
│   │
│   ├── worker/
│   │   └── MaintenanceCheckWorker.kt   # WorkManager Notification
│   │
│   └── export/
│       ├── CsvExporter.kt              # CSV Export
│       └── ShareHelper.kt              # Share Intent
│
└── ui/                                 # -- Presentation Layer --
    ├── navigation/
    │   ├── AppNavigation.kt            # NavHost
    │   └── AppViewModelFactory.kt
    │
    ├── screens/
    │   ├── home/, add_car/, add_record/, history/, trips/, calendar/, settings/, tutorial/
    │
    └── theme/
        ├── Color.kt, Theme.kt, Type.kt # Material 3 Theme & Typography
```
</details>

---

## Database Schema (ERD)

<details>
<summary><b>คลิกเพื่อดูแผนภาพ ERD</b></summary>

```mermaid
erDiagram
    CARS ||--o{ MAINTENANCES : has
    CARS ||--o{ EXPENSES : has
    CARS ||--o{ TRIPS : has

    CARS {
        int id PK
        string brand
        string model
        int year
        int currentMileage
        string imagePath "nullable"
    }

    MAINTENANCES {
        int id PK
        int carId FK
        string type
        long date
        int mileage
        double cost
        string notes "nullable"
        string imagePath "nullable"
    }

    EXPENSES {
        int id PK
        int carId FK
        string type
        long date
        double amount
        string notes "nullable"
        double liters "nullable"
        int mileageAtFill "nullable"
        string imagePath "nullable"
    }

    TRIPS {
        int id PK
        int carId FK
        long starttime
        long endTime
        int distanceKm
        boolean isActive
    }
```
*มีการใช้ **Foreign Key** `carId` เชื่อมกับตาราง `CARS` พร้อมเปิดใช้งาน **ON DELETE CASCADE** เพื่อความสมบูรณ์ของข้อมูล*
</details>

---

## App Flow Diagram

[คลิกเพื่อดู Wireframe บน Figma](https://www.figma.com/make/Y4TiGHUkDXxkPVDzhKaipi/%E0%B8%AA%E0%B8%A3%E0%B9%89%E0%B8%B2%E0%B8%87-Wireframe-%E0%B9%81%E0%B8%AD%E0%B8%9B-Android?t=PbRGAL5cuc74Za5c-1)

แอปพลิเคชันถูกออกแบบออกเป็น 6 User Flows หลัก (มากกว่า 15 Micro-flows):

<details>
<summary><b>คลิกเพื่อดูแผนภาพการไหลของแอป (Flow Diagram)</b></summary>

```mermaid
---
config:
  layout: elk
---
graph TD
    HomeScreen["🏠 Home Screen<br/>(iDrive Branding)"]
    
    HomeScreen -->|Start| GarageView["🚗 Garage View<br/>(Select Car)"]
    HomeScreen -->|Tutorial| TutorialPage["📚 Tutorial<br/>(3 Steps)"]
    HomeScreen -->|Settings| SettingsPage["⚙️ Settings"]
    
    GarageView -->|Select Car| Dashboard["📊 Dashboard"]
    
    Dashboard -->|Hero Image| DashboardContent["Dashboard Content"]
    DashboardContent -->|Alert Card| AlertCard["⚠️ Alerts"]
    DashboardContent -->|Fuel Economy| FuelEcon["⛽ Fuel Economy"]
    DashboardContent -->|Quick Nav| QuickNav{"Quick Navigation"}
    DashboardContent -->|Add Record| AddRecord["➕ Add/Edit Record"]
    
    QuickNav -->|History| HistoryPage["📈 History<br/>(Graphs, Search, Filter)"]
    QuickNav -->|Trips| TripsPage["🛣️ Trips<br/>(Auto-tracked)"]
    QuickNav -->|Calendar| CalendarPage["📅 Calendar"]
    
    AddRecord -->|Tab: Maintenance| MaintenanceTab["🔧 Maintenance Tab"]
    AddRecord -->|Tab: Expense| ExpenseTab["💰 Expense Tab"]
    MaintenanceTab -->|Fuel Fields| FuelFields["⛽ Fuel Fields"]
    ExpenseTab -->|Photo Pick| PhotoPicker["📷 Photo Picker"]
    FuelFields -->|Save| SaveButton["✅ Save Record"]
    PhotoPicker -->|Save| SaveButton
    
    TutorialPage -->|Info| TutorialContent["3 Steps Guide"]
    
    SettingsPage -->|Options| SettingsContent["Settings Options"]
    SettingsContent -->|Dark Mode| DarkMode["🌙 Dark Mode"]
    SettingsContent -->|Language| Language["🌐 Language"]
    SettingsContent -->|Intervals| Intervals["⏱️ Intervals"]
    SettingsContent -->|AutoTrack| AutoTrack["📍 AutoTrack"]
    SettingsContent -->|Export| ExportCSV["📤 Export CSV"]
    SettingsContent -->|Version| Version["ℹ️ Version Info"]
    
    classDef home fill:#eef2ff,stroke:#818cf8,color:#1e1b4b
    classDef garage fill:#f0fdfa,stroke:#2dd4bf,color:#1e1b4b
    classDef main fill:#f0f9ff,stroke:#38bdf8,color:#1e1b4b
    classDef secondary fill:#f5f3ff,stroke:#a78bfa,color:#1e1b4b
    classDef action fill:#fdf4ff,stroke:#e879f9,color:#1e1b4b
    classDef settings fill:#fff7ed,stroke:#fb923c,color:#1e1b4b
    
    class HomeScreen home
    class GarageView garage
    class Dashboard main
    class HistoryPage,TripsPage,CalendarPage secondary
    class AddRecord,SaveButton action
    class SettingsPage,TutorialPage settings
```
</details>

---

## วิธีการติดตั้งและใช้งาน (Installation)

### Prerequisites
- **Android Studio** Ladybug (2024.2+) หรือใหม่กว่า
- **JDK 11** ขึ้นไป
- **Android SDK** API Level 36
- Physical Device (สำหรับฟีเจอร์ Activity Recognition)

### ขั้นตอนการรันโปรเจกต์
```bash
# 1. Clone Repository นี้ลงบนเครื่องของคุณ
git clone https://github.com/<your-username>/CP213_176_LearnAndroid.git

# 2. เปิด Android Studio แล้วเลือก Open ไปที่โฟลเดอร์ Project_App

# 3. รอให้ Gradle Sync และดาวน์โหลด Dependencies เสร็จสิ้น

# 4. เลือก Device หรือ Emulator และกด Run (▶️)
```

<details>
<summary><b>สิทธิ์การใช้งานแอป (Permissions)</b></summary>

| Permission | วัตถุประสงค์ |
|---|---|
| `INTERNET` | โหลดรูปภาพจาก URL |
| `POST_NOTIFICATIONS` | แจ้งเตือนบำรุงรักษารถประจำวัน |
| `ACTIVITY_RECOGNITION` | ตรวจจับการเคลื่อนที่ (IN_VEHICLE) อัตโนมัติ |
| `ACCESS_FINE_LOCATION` | ประมวลผลระยะทางผ่าน GPS |
| `ACCESS_COARSE_LOCATION` | ระบบตำแหน่งที่ตั้งโดยประมาณ |
| `FOREGROUND_SERVICE` | ให้บริการ Trip Tracking ทำงานเบื้องหลังได้ |
</details>

---

## สิ่งที่ได้เรียนรู้ (Lessons Learned)
- **Jetpack Compose** (Declarative UI)
- **Room Database** (Entity, DAO, Foreign Key, Migration)
- **MVVM Architecture** (ViewModel + StateFlow)
- **Kotlin Coroutines & Flow** (Asynchronous operations)
- **Navigation Compose** (Screen Routing)
- **Material Design 3** (Dynamic Colors, Theming)
- **WorkManager & Background Services** (Periodic Tasks & Location Tracking)
