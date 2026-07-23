# PhotographyPortfolio

Website manajemen portfolio fotografi berbasis **Java Spring Boot**, dengan sistem login 2 role (Admin & User) dan CRUD lengkap untuk Foto, Kamera, Lokasi, dan Kategori.

## Tech Stack

- Java 17
- Spring Boot 3.3.4
- Maven
- Spring MVC
- Spring Data JPA (Hibernate)
- Spring Security
- Thymeleaf + Bootstrap 5
- MySQL
- Lombok
- Bean Validation (Jakarta Validation)

---

## 1. Persiapan / Requirements

Pastikan sudah terinstall di komputer Anda:

- **JDK 17** (`java -version`)
- **Maven 3.9+** (`mvn -version`) — atau gunakan Maven Wrapper bila tersedia
- **MySQL Server** (versi 8.x direkomendasikan) yang sedang berjalan di `localhost:3306`
- IntelliJ IDEA / Eclipse / VS Code (opsional, untuk membuka project)

---

## 2. Konfigurasi Database

Tabel dan data contoh **dibuat otomatis** — Anda hanya perlu membuat **database kosong** satu kali (fitur auto-create database dari MySQL Connector kadang tidak konsisten di semua environment, jadi langkah manual ini yang paling andal).

1. Pastikan **MySQL Server sudah running** (jalankan MySQL dari XAMPP Control Panel, klik **Start**).
2. Buka **phpMyAdmin** (`http://localhost/phpmyadmin`), klik tab **Databases**, ketik nama `photography_portfolio`, klik **Create**. Biarkan kosong, jangan buat tabel apa pun.
3. Pastikan kredensial di `src/main/resources/application.properties` sesuai MySQL Anda (default XAMPP `root` tanpa password sudah sesuai default project):
   ```properties
   spring.datasource.username=root
   spring.datasource.password=
   ```
4. Jalankan aplikasi (lihat langkah 4 di bawah). Saat start:
   - Hibernate akan otomatis membuat semua tabel sesuai entity (`spring.jpa.hibernate.ddl-auto=update`).
   - `DataInitializer` akan otomatis mengisi data contoh (1 admin, 2 user, 5 kamera, 5 lokasi, 5 kategori, 10 foto) **hanya jika tabel masih kosong** — aman dijalankan berkali-kali tanpa duplikasi data.

Setelah aplikasi berjalan, buka phpMyAdmin untuk melihat tabel & data yang sudah otomatis terbentuk.

> **Opsional:** file `database.sql` tetap disediakan di root project jika Anda ingin membuat database secara manual lengkap dengan tabel & data (misalnya untuk keperluan submit tugas / dokumentasi), tapi tidak wajib dijalankan selama Anda sudah membuat database kosong seperti di atas.

---

## 3. Cara Install

1. Extract file `PhotographyPortfolioCRUD.zip`.
2. Buka folder hasil extract di IntelliJ IDEA / Eclipse sebagai **Maven Project**, atau via terminal masuk ke folder project:
   ```bash
   cd PhotographyPortfolio
   ```
3. Install dependency & build:
   ```bash
   mvn clean install
   ```

---

## 4. Cara Menjalankan Project

Melalui terminal:

```bash
mvn spring-boot:run
```

Atau jalankan class `PhotographyPortfolioApplication.java` langsung dari IDE (klik kanan → Run).

Setelah berjalan, buka browser ke:

```
http://localhost:8080
```

Anda akan diarahkan otomatis ke halaman login.

---

## 5. Username & Password Login (Default)

| Role  | Username / Email     | Password   |
|-------|-----------------------|------------|
| Admin | `admin`               | `admin123` |
| User  | `budi@example.com`    | `user123`  |
| User  | `siti@example.com`    | `user123`  |

- **Admin** login menggunakan **username**.
- **User** login menggunakan **email**.
- Setelah login, Admin diarahkan ke `/dashboard`, User diarahkan ke `/foto/list` (galeri foto).

---

## 6. Fitur

### Admin
- Login / Logout
- Dashboard (ringkasan jumlah User, Foto, Kamera, Lokasi, Kategori)
- CRUD User
- CRUD Foto (dengan dropdown Kamera, Lokasi, Kategori, User)
- CRUD Kamera
- CRUD Lokasi
- CRUD Kategori
- Search sederhana pada setiap halaman list
- Pagination sederhana pada tabel Foto (client-side)

### User
- Login / Logout
- **Daftar akun sendiri (Register)** — calon User bisa membuat akun baru lewat halaman `/register` tanpa perlu dibuatkan oleh Admin
- Melihat daftar foto (galeri)
- Melihat detail foto

### Umum
- Validasi form (wajib diisi, format email valid, dll)
- Alert sukses/error
- Tampilan responsive (Bootstrap 5)

---

## 7. Catatan tentang Gambar Foto

Sesuai ketentuan, aplikasi ini **tidak melakukan upload file** — field `namaFile` pada tabel Foto hanya menyimpan **nama file** (String). Gambar aktual harus diletakkan di folder:

```
src/main/resources/static/images/
```

Project ini sudah menyertakan 10 gambar contoh (`foto1.jpg` s/d `foto10.jpg`) beserta `placeholder.jpg` sebagai fallback otomatis jika nama file yang diisi tidak ditemukan.

Untuk menambahkan foto baru:
1. Copy file gambar ke folder `static/images/`
2. Saat Tambah/Edit Foto di halaman Admin, isi field **Nama File Gambar** dengan nama file tersebut (contoh: `foto11.jpg`)

---

## 8. Struktur Project

```
PhotographyPortfolio/
├── pom.xml
├── database.sql
├── README.md
└── src/
    └── main/
        ├── java/com/photography/portfolio/
        │   ├── PhotographyPortfolioApplication.java
        │   ├── config/          # SecurityConfig, CustomUserDetailsService, WebConfig
        │   ├── controller/      # Semua Controller (Admin & User)
        │   ├── dto/             # DashboardStatsDto
        │   ├── entity/          # Admin, User, Kamera, Lokasi, Kategori, Foto
        │   ├── repository/      # Spring Data JPA Repository
        │   ├── service/         # Service interface
        │   └── service/impl/    # Service implementation
        └── resources/
            ├── application.properties
            ├── templates/       # Thymeleaf views
            │   ├── fragments/   # navbar, sidebar, alert, scripts
            │   ├── user/ kamera/ lokasi/ kategori/ foto/  (CRUD Admin)
            │   ├── userview/    # galeri foto untuk role USER
            │   ├── error/       # halaman 403
            │   ├── login.html
            │   └── dashboard.html
            └── static/
                ├── css/style.css
                ├── js/script.js
                └── images/      # sample images + placeholder.jpg
```

---

## 9. Troubleshooting

- **Error koneksi database** → pastikan MySQL sedang running dan kredensial di `application.properties` sudah sesuai.
- **Port 8080 sudah dipakai** → ubah `server.port` di `application.properties`.
- **Gambar tidak muncul** → pastikan nama file yang diisi pada form Foto benar-benar ada di folder `static/images/`, atau biarkan fallback `placeholder.jpg` tampil otomatis.
- **Tabel tidak terbentuk otomatis** → pastikan `spring.jpa.hibernate.ddl-auto=update` dan user MySQL memiliki hak akses `CREATE`/`ALTER` pada database.

## 10. Menyimpan Project ke GitHub

Project ini sudah disiapkan supaya aman di-push ke GitHub — kredensial database **tidak di-hardcode** di `application.properties`, melainkan lewat environment variable (dengan default untuk MySQL lokal supaya tetap langsung jalan tanpa konfigurasi tambahan). Jadi aman walau repo-nya publik.

### Langkah push ke GitHub

1. Pastikan [Git](https://git-scm.com/downloads) sudah terinstall (`git --version` di terminal).
2. Di folder project, jalankan:
   ```bash
   git init
   git add .
   git commit -m "Initial commit - PhotographyPortfolio CRUD"
   ```
3. Buat repository baru di [github.com/new](https://github.com/new) (jangan centang "Add README", biar tidak konflik).
4. Hubungkan & push:
   ```bash
   git remote add origin https://github.com/USERNAME/NAMA-REPO.git
   git branch -M main
   git push -u origin main
   ```

### Mengganti database (misalnya ke Supabase/PostgreSQL) tanpa mengubah kode

Set environment variable berikut di komputer/server tempat aplikasi dijalankan (nama variabelnya sudah disiapkan di `application.properties`):

| Environment Variable | Contoh nilai (Supabase/Postgres) |
|---|---|
| `DB_URL` | `jdbc:postgresql://db.xxxxx.supabase.co:5432/postgres` |
| `DB_USERNAME` | `postgres` |
| `DB_PASSWORD` | `password_supabase_kamu` |
| `DB_DRIVER` | `org.postgresql.Driver` |
| `JPA_DIALECT` | `org.hibernate.dialect.PostgreSQLDialect` |

Kalau environment variable ini tidak di-set, aplikasi otomatis fallback ke konfigurasi MySQL lokal default — jadi tetap aman untuk development sehari-hari.

> Catatan: untuk benar-benar memakai PostgreSQL, dependency `mysql-connector-j` di `pom.xml` perlu diganti/ditambah dengan driver PostgreSQL (`org.postgresql:postgresql`). Ini belum termasuk di project saat ini karena masih menyasar MySQL sebagai database utama sesuai permintaan awal tugas.

---

Selamat mencoba! 📸
