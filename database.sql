-- ============================================================
-- PhotographyPortfolio - Database Setup Script
-- ============================================================
-- Cara pakai:
--   1. Jalankan seluruh file ini di MySQL (mysql -u root -p < database.sql)
--      ATAU copy-paste ke MySQL Workbench / phpMyAdmin / DBeaver.
--   2. Aplikasi Spring Boot akan otomatis menyesuaikan skema tabel
--      (spring.jpa.hibernate.ddl-auto=update) saat pertama kali dijalankan,
--      jadi CREATE TABLE di bawah ini bersifat opsional / untuk jaga-jaga.
-- ============================================================

CREATE DATABASE IF NOT EXISTS photography_portfolio
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE photography_portfolio;

-- ============================================================
-- TABLE STRUCTURE
-- ============================================================

CREATE TABLE IF NOT EXISTS admin (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    nama VARCHAR(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS kamera (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama_kamera VARCHAR(150) NOT NULL,
    merk VARCHAR(100) NOT NULL,
    deskripsi VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS lokasi (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama_lokasi VARCHAR(150) NOT NULL,
    kota VARCHAR(100) NOT NULL,
    deskripsi VARCHAR(500)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS kategori (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS foto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    judul VARCHAR(200) NOT NULL,
    nama_file VARCHAR(255) NOT NULL,
    deskripsi VARCHAR(1000),
    tanggal_upload DATE NOT NULL,
    kamera_id BIGINT NOT NULL,
    lokasi_id BIGINT NOT NULL,
    kategori_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT fk_foto_kamera FOREIGN KEY (kamera_id) REFERENCES kamera(id),
    CONSTRAINT fk_foto_lokasi FOREIGN KEY (lokasi_id) REFERENCES lokasi(id),
    CONSTRAINT fk_foto_kategori FOREIGN KEY (kategori_id) REFERENCES kategori(id),
    CONSTRAINT fk_foto_user FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- SAMPLE DATA
-- ============================================================

-- Password admin (admin123) sudah di-hash menggunakan BCrypt
INSERT INTO admin (username, password, nama) VALUES
('admin', '$2b$10$T.8O9b7Qih9uat2TPKgpsuO77rpGuwQI3LVbqF5VgxHiQLBPsqhu.', 'Administrator');

-- Password user (user123) sudah di-hash menggunakan BCrypt
INSERT INTO users (nama, email, password) VALUES
('Budi Santoso', 'budi@example.com', '$2b$10$gptp647LZUO6.vbUKWrZdebd0Oyj1PUvWOAE5IOu2oWYjvOCsH.Om'),
('Siti Aminah', 'siti@example.com', '$2b$10$gptp647LZUO6.vbUKWrZdebd0Oyj1PUvWOAE5IOu2oWYjvOCsH.Om');

INSERT INTO kamera (nama_kamera, merk, deskripsi) VALUES
('Canon EOS R6', 'Canon', 'Kamera mirrorless full-frame dengan performa tinggi untuk foto dan video'),
('Sony A7III', 'Sony', 'Kamera mirrorless andalan untuk fotografi profesional'),
('Nikon D750', 'Nikon', 'Kamera DSLR full-frame dengan kualitas gambar sangat baik'),
('Fujifilm X-T4', 'Fujifilm', 'Kamera mirrorless APS-C dengan warna khas Fujifilm'),
('Canon EOS 90D', 'Canon', 'Kamera DSLR APS-C serba guna untuk pemula hingga menengah');

INSERT INTO lokasi (nama_lokasi, kota, deskripsi) VALUES
('Pantai Kuta', 'Bali', 'Pantai populer dengan pemandangan matahari terbenam yang indah'),
('Gunung Bromo', 'Probolinggo', 'Kawasan gunung berapi dengan pemandangan matahari terbit ikonik'),
('Studio Jakarta', 'Jakarta', 'Studio foto indoor dengan pencahayaan lengkap'),
('Hutan Pinus', 'Bandung', 'Hutan pinus yang teduh, cocok untuk foto prewedding'),
('Malioboro', 'Yogyakarta', 'Kawasan jalan legendaris untuk foto street photography');

INSERT INTO kategori (nama_kategori) VALUES
('Wedding'),
('Nature'),
('Street'),
('Portrait'),
('Travel');

INSERT INTO foto (judul, nama_file, deskripsi, tanggal_upload, kamera_id, lokasi_id, kategori_id, user_id) VALUES
('Sunset Wedding di Kuta', 'foto1.jpg', 'Momen pernikahan romantis saat matahari terbenam di Pantai Kuta', '2026-01-15', 1, 1, 1, 1),
('Sunrise Bromo', 'foto2.jpg', 'Keindahan matahari terbit di Gunung Bromo', '2026-01-20', 3, 2, 2, 2),
('Street Life Malioboro', 'foto3.jpg', 'Suasana ramai jalan Malioboro di sore hari', '2026-02-01', 4, 5, 3, 1),
('Portrait Studio Session', 'foto4.jpg', 'Sesi foto portrait dengan pencahayaan studio', '2026-02-05', 5, 3, 4, 2),
('Explore Raja Ampat', 'foto5.jpg', 'Keindahan alam bawah laut dan pulau Raja Ampat', '2026-02-10', 2, 2, 5, 1),
('Prewedding di Hutan Pinus', 'foto6.jpg', 'Sesi prewedding dengan latar hutan pinus yang syahdu', '2026-02-14', 1, 4, 1, 2),
('Kabut Pagi Danau Toba', 'foto7.jpg', 'Pemandangan kabut pagi di kawasan Danau Toba', '2026-02-18', 3, 2, 2, 1),
('Suasana Kota Tua', 'foto8.jpg', 'Aktivitas warga di kawasan Kota Tua Jakarta', '2026-02-22', 4, 3, 3, 2),
('Portrait Klasik', 'foto9.jpg', 'Foto portrait dengan gaya klasik dan pencahayaan lembut', '2026-02-25', 5, 3, 4, 1),
('Menjelajahi Labuan Bajo', 'foto10.jpg', 'Perjalanan menjelajahi keindahan Labuan Bajo', '2026-03-01', 2, 2, 5, 2);
