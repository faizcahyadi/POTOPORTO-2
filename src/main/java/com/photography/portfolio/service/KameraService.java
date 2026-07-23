package com.photography.portfolio.service;

import com.photography.portfolio.entity.Kamera;

import java.util.List;
import java.util.Optional;

public interface KameraService {
    List<Kamera> findAll();
    List<Kamera> search(String keyword);
    Optional<Kamera> findById(Long id);
    Kamera save(Kamera kamera);
    void deleteById(Long id);
    long count();
}
