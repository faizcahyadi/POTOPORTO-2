package com.photography.portfolio.service;

import com.photography.portfolio.entity.Kategori;

import java.util.List;
import java.util.Optional;

public interface KategoriService {
    List<Kategori> findAll();
    List<Kategori> search(String keyword);
    Optional<Kategori> findById(Long id);
    Kategori save(Kategori kategori);
    void deleteById(Long id);
    long count();
}
