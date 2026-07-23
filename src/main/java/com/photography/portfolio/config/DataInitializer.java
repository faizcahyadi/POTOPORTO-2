package com.photography.portfolio.config;

import com.photography.portfolio.entity.*;
import com.photography.portfolio.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * Mengisi data contoh (sample data) secara OTOMATIS saat aplikasi pertama kali
 * dijalankan, selama tabel-tabel terkait masih kosong.
 * Database & tabel dibuat otomatis oleh MySQL Connector (createDatabaseIfNotExist=true)
 * dan Hibernate (spring.jpa.hibernate.ddl-auto=update) — jadi tidak perlu
 * menjalankan database.sql secara manual.
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final KameraRepository kameraRepository;
    private final LokasiRepository lokasiRepository;
    private final KategoriRepository kategoriRepository;
    private final FotoRepository fotoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdmin();
        List<User> users = seedUsers();
        List<Kamera> kameraList = seedKamera();
        List<Lokasi> lokasiList = seedLokasi();
        List<Kategori> kategoriList = seedKategori();
        seedFoto(users, kameraList, lokasiList, kategoriList);
    }

    private void seedAdmin() {
        if (adminRepository.count() > 0) return;
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setNama("Administrator");
        adminRepository.save(admin);
    }

    private List<User> seedUsers() {
        if (userRepository.count() > 0) return userRepository.findAll();

        User budi = new User();
        budi.setNama("Budi Santoso");
        budi.setEmail("budi@example.com");
        budi.setPassword(passwordEncoder.encode("user123"));

        User siti = new User();
        siti.setNama("Siti Aminah");
        siti.setEmail("siti@example.com");
        siti.setPassword(passwordEncoder.encode("user123"));

        return userRepository.saveAll(List.of(budi, siti));
    }

    private List<Kamera> seedKamera() {
        if (kameraRepository.count() > 0) return kameraRepository.findAll();

        return kameraRepository.saveAll(List.of(
                new Kamera(null, "Canon EOS R6", "Canon", "Kamera mirrorless full-frame dengan performa tinggi untuk foto dan video"),
                new Kamera(null, "Sony A7III", "Sony", "Kamera mirrorless andalan untuk fotografi profesional"),
                new Kamera(null, "Nikon D750", "Nikon", "Kamera DSLR full-frame dengan kualitas gambar sangat baik"),
                new Kamera(null, "Fujifilm X-T4", "Fujifilm", "Kamera mirrorless APS-C dengan warna khas Fujifilm"),
                new Kamera(null, "Canon EOS 90D", "Canon", "Kamera DSLR APS-C serba guna untuk pemula hingga menengah")
        ));
    }

    private List<Lokasi> seedLokasi() {
        if (lokasiRepository.count() > 0) return lokasiRepository.findAll();

        return lokasiRepository.saveAll(List.of(
                new Lokasi(null, "Pantai Kuta", "Bali", "Pantai populer dengan pemandangan matahari terbenam yang indah"),
                new Lokasi(null, "Gunung Bromo", "Probolinggo", "Kawasan gunung berapi dengan pemandangan matahari terbit ikonik"),
                new Lokasi(null, "Studio Jakarta", "Jakarta", "Studio foto indoor dengan pencahayaan lengkap"),
                new Lokasi(null, "Hutan Pinus", "Bandung", "Hutan pinus yang teduh, cocok untuk foto prewedding"),
                new Lokasi(null, "Malioboro", "Yogyakarta", "Kawasan jalan legendaris untuk foto street photography")
        ));
    }

    private List<Kategori> seedKategori() {
        if (kategoriRepository.count() > 0) return kategoriRepository.findAll();

        return kategoriRepository.saveAll(List.of(
                new Kategori(null, "Wedding"),
                new Kategori(null, "Nature"),
                new Kategori(null, "Street"),
                new Kategori(null, "Portrait"),
                new Kategori(null, "Travel")
        ));
    }

    private void seedFoto(List<User> users, List<Kamera> kameraList, List<Lokasi> lokasiList, List<Kategori> kategoriList) {
        if (fotoRepository.count() > 0) return;
        if (users.size() < 2 || kameraList.size() < 5 || lokasiList.size() < 5 || kategoriList.size() < 5) return;

        User budi = users.get(0);
        User siti = users.get(1);

        fotoRepository.saveAll(List.of(
                foto("Sunset Wedding di Kuta", "foto1.jpg", "Momen pernikahan romantis saat matahari terbenam di Pantai Kuta",
                        LocalDate.of(2026, 1, 15), kameraList.get(0), lokasiList.get(0), kategoriList.get(0), budi),
                foto("Sunrise Bromo", "foto2.jpg", "Keindahan matahari terbit di Gunung Bromo",
                        LocalDate.of(2026, 1, 20), kameraList.get(2), lokasiList.get(1), kategoriList.get(1), siti),
                foto("Street Life Malioboro", "foto3.jpg", "Suasana ramai jalan Malioboro di sore hari",
                        LocalDate.of(2026, 2, 1), kameraList.get(3), lokasiList.get(4), kategoriList.get(2), budi),
                foto("Portrait Studio Session", "foto4.jpg", "Sesi foto portrait dengan pencahayaan studio",
                        LocalDate.of(2026, 2, 5), kameraList.get(4), lokasiList.get(2), kategoriList.get(3), siti),
                foto("Explore Raja Ampat", "foto5.jpg", "Keindahan alam bawah laut dan pulau Raja Ampat",
                        LocalDate.of(2026, 2, 10), kameraList.get(1), lokasiList.get(1), kategoriList.get(4), budi),
                foto("Prewedding di Hutan Pinus", "foto6.jpg", "Sesi prewedding dengan latar hutan pinus yang syahdu",
                        LocalDate.of(2026, 2, 14), kameraList.get(0), lokasiList.get(3), kategoriList.get(0), siti),
                foto("Kabut Pagi Danau Toba", "foto7.jpg", "Pemandangan kabut pagi di kawasan Danau Toba",
                        LocalDate.of(2026, 2, 18), kameraList.get(2), lokasiList.get(1), kategoriList.get(1), budi),
                foto("Suasana Kota Tua", "foto8.jpg", "Aktivitas warga di kawasan Kota Tua Jakarta",
                        LocalDate.of(2026, 2, 22), kameraList.get(3), lokasiList.get(2), kategoriList.get(2), siti),
                foto("Portrait Klasik", "foto9.jpg", "Foto portrait dengan gaya klasik dan pencahayaan lembut",
                        LocalDate.of(2026, 2, 25), kameraList.get(4), lokasiList.get(2), kategoriList.get(3), budi),
                foto("Menjelajahi Labuan Bajo", "foto10.jpg", "Perjalanan menjelajahi keindahan Labuan Bajo",
                        LocalDate.of(2026, 3, 1), kameraList.get(1), lokasiList.get(1), kategoriList.get(4), siti)
        ));
    }

    private Foto foto(String judul, String namaFile, String deskripsi, LocalDate tanggal,
                       Kamera kamera, Lokasi lokasi, Kategori kategori, User user) {
        Foto f = new Foto();
        f.setJudul(judul);
        f.setNamaFile(namaFile);
        f.setDeskripsi(deskripsi);
        f.setTanggalUpload(tanggal);
        f.setKamera(kamera);
        f.setLokasi(lokasi);
        f.setKategori(kategori);
        f.setUser(user);
        return f;
    }
}
