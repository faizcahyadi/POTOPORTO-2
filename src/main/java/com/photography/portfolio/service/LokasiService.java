package com.photography.portfolio.service;

import com.photography.portfolio.entity.Lokasi;

import java.util.List;
import java.util.Optional;

public interface LokasiService {
    List<Lokasi> findAll();
    List<Lokasi> search(String keyword);
    Optional<Lokasi> findById(Long id);
    Lokasi save(Lokasi lokasi);
    void deleteById(Long id);
    long count();
}
