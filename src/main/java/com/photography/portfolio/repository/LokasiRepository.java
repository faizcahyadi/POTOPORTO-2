package com.photography.portfolio.repository;

import com.photography.portfolio.entity.Lokasi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LokasiRepository extends JpaRepository<Lokasi, Long> {
    List<Lokasi> findByNamaLokasiContainingIgnoreCaseOrKotaContainingIgnoreCase(String namaLokasi, String kota);
}
