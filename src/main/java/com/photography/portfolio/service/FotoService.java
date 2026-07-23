package com.photography.portfolio.service;

import com.photography.portfolio.entity.Foto;

import java.util.List;
import java.util.Optional;

public interface FotoService {
    List<Foto> findAll();
    List<Foto> search(String keyword);
    Optional<Foto> findById(Long id);
    Foto save(Foto foto);
    void deleteById(Long id);
    long count();
}
