# Tugas Praktikum Minggu ke 7 — MyProfileApp (Local Data Storage)

**Nama:** Diwan Ramadhani Dwi Putra  
**NIM:** 123140116  
**Mata Kuliah:** Pengembangan Aplikasi Mobile (IF25-22017)

## Deskripsi

Upgrade dari Notes App minggu sebelumnya, sekarang data notes beneran tersimpan di local database pakai **SQLDelight** — jadi nggak hilang lagi setiap kali app ditutup. Untuk settings seperti dark mode, nama, dan bio profil disimpan pakai **multiplatform-settings** (pengganti DataStore yang KMP-compatible).

Arsitekturnya dipisah jadi dua modul: `composeApp` untuk UI layer, dan `shared` untuk logic + data layer. Repository jadi satu-satunya pintu masuk data ke ViewModel, dan UI otomatis update via Flow setiap kali ada perubahan di database.

Fitur yang diimplementasikan:
- **SQLDelight** — database lokal dengan `.sq` schema file, semua query type-safe
- **CRUD operations** — tambah, lihat, edit, hapus catatan
- **Search** — pencarian real-time pakai `flatMapLatest` + query `LIKE` di SQL
- **Toggle Favorite** — disimpan di kolom `isFavorite` di database, persist setelah app ditutup
- **Settings persistent** — dark mode, nama, bio tersimpan lewat `multiplatform-settings` (SharedPreferences di Android)
- **UI States** — loading indicator, empty state, content state sudah proper
- **Offline-first** — semua data dari local DB, nggak butuh internet sama sekali

## Screenshot

<img src="SS.png" width="300">
<img src="SS1.png" width="300">
<img src="SS2.png" width="300">
<img src="SS3.png" width="300">

## Database Schema

```sql
CREATE TABLE Note (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    title      TEXT    NOT NULL,
    content    TEXT    NOT NULL,
    isFavorite INTEGER NOT NULL DEFAULT 0,
    created_at INTEGER NOT NULL,
    updated_at INTEGER NOT NULL
);
```

Query yang tersedia: `selectAll`, `selectFavorites`, `search`, `selectById`, `insert`, `update`, `delete`, `toggleFavorite`.

## Struktur Proyek

```
MyProfileApp/
├── composeApp/                          → UI layer (Android)
│   └── src/androidMain/kotlin/com/diwan/myprofileapp/
│       ├── MainActivity.kt
│       ├── navigation/
│       │   ├── AppNavigation.kt         → NavHost + Scaffold + BottomNavBar + AppThemeWrapper
│       │   └── Screen.kt                → sealed class routes
│       └── screens/
│           ├── NoteListScreen.kt        → list + search bar + loading/empty state
│           ├── NoteScreens.kt           → AddNoteScreen, NoteDetailScreen, EditNoteScreen
│           ├── FavoritesScreen.kt
│           └── ProfileScreen.kt         → ProfileHeader, ProfileCard, dark mode toggle, EditProfileScreen
│
└── shared/                              → Data & logic layer (KMP)
    └── src/
        ├── commonMain/
        │   ├── sqldelight/com/diwan/myprofileapp/db/
        │   │   └── Note.sq              → SQL schema & semua queries
        │   └── kotlin/com/diwan/myprofileapp/shared/
        │       ├── data/
        │       │   ├── DatabaseDriverFactory.kt  → expect class
        │       │   ├── NoteItem.kt               → domain model
        │       │   ├── NoteRepository.kt         → CRUD + Flow
        │       │   └── SettingsRepository.kt     → key-value settings
        │       └── viewmodel/
        │           ├── NoteViewModel.kt          → search, CRUD, favorites
        │           └── ProfileViewModel.kt       → profile state + dark mode
        └── androidMain/kotlin/com/diwan/myprofileapp/shared/
            ├── data/
            │   └── DatabaseDriverFactory.kt     → actual (AndroidSqliteDriver)
            └── viewmodel/
                └── ViewModelFactory.kt          → inject repo ke ViewModel
```

## Cara Menjalankan

1. Clone atau buka proyek di **Android Studio**
2. Pastikan sudah ada:
   - Android Studio Hedgehog atau lebih baru
   - JDK 11
   - Android SDK min API 24
3. Jalankan Gradle sync — SQLDelight akan generate Kotlin code dari `Note.sq` secara otomatis
4. Run ke emulator atau perangkat fisik

> Kalau muncul error `No database found`, pastikan file `Note.sq` ada di path yang benar lalu run Gradle sync ulang.

## Dependensi Utama

```toml
sqldelight        = "2.0.2"
multiplatform-settings = "1.1.1"
kotlinx-coroutines = "1.8.1"
navigation-compose = "2.8.0"
lifecycle         = "2.8.5"
```
