package com.photography.portfolio.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama tidak boleh kosong")
    @Column(name = "nama", nullable = false, length = 150)
    private String nama;

    @NotBlank(message = "Email tidak boleh kosong")
    @Email(message = "Format email tidak valid")
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    // Catatan: tidak menggunakan @NotBlank di sini karena field ini juga dipakai
    // pada form Edit User, di mana password boleh dikosongkan (artinya password lama dipertahankan).
    // Validasi "wajib diisi saat tambah data baru" dilakukan secara manual di UserController.
    @Column(name = "password", nullable = false, length = 255)
    private String password;
}
