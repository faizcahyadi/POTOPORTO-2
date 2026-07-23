package com.photography.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lokasi")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lokasi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama lokasi tidak boleh kosong")
    @Column(name = "nama_lokasi", nullable = false, length = 150)
    private String namaLokasi;

    @NotBlank(message = "Kota tidak boleh kosong")
    @Column(name = "kota", nullable = false, length = 100)
    private String kota;

    @Column(name = "deskripsi", length = 500)
    private String deskripsi;
}
