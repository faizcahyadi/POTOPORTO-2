package com.photography.portfolio.repository;

import com.photography.portfolio.entity.Kategori;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KategoriRepository extends JpaRepository<Kategori, Long> {
    List<Kategori> findByNamaKategoriContainingIgnoreCase(String namaKategori);
}
