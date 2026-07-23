package com.photography.portfolio.repository;

import com.photography.portfolio.entity.Foto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FotoRepository extends JpaRepository<Foto, Long> {
    List<Foto> findByJudulContainingIgnoreCase(String judul);
}
