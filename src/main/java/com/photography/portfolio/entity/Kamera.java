package com.photography.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kamera")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kamera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama kamera tidak boleh kosong")
    @Column(name = "nama_kamera", nullable = false, length = 150)
    private String namaKamera;

    @NotBlank(message = "Merk tidak boleh kosong")
    @Column(name = "merk", nullable = false, length = 100)
    private String merk;

    @Column(name = "deskripsi", length = 500)
    private String deskripsi;
}
