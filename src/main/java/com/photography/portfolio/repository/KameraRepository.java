package com.photography.portfolio.repository;

import com.photography.portfolio.entity.Kamera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KameraRepository extends JpaRepository<Kamera, Long> {
    List<Kamera> findByNamaKameraContainingIgnoreCaseOrMerkContainingIgnoreCase(String namaKamera, String merk);
}
