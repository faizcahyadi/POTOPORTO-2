package com.photography.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "foto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Judul tidak boleh kosong")
    @Column(name = "judul", nullable = false, length = 200)
    private String judul;

    @NotBlank(message = "Nama file gambar tidak boleh kosong")
    @Column(name = "nama_file", nullable = false, length = 255)
    private String namaFile;

    @Column(name = "deskripsi", length = 1000)
    private String deskripsi;

    @NotNull(message = "Tanggal upload tidak boleh kosong")
    @Column(name = "tanggal_upload", nullable = false)
    private LocalDate tanggalUpload;

    @NotNull(message = "Kamera wajib dipilih")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kamera_id", nullable = false)
    private Kamera kamera;

    @NotNull(message = "Lokasi wajib dipilih")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lokasi_id", nullable = false)
    private Lokasi lokasi;

    @NotNull(message = "Kategori wajib dipilih")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kategori_id", nullable = false)
    private Kategori kategori;

    @NotNull(message = "User wajib dipilih")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
