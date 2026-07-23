package com.photography.portfolio.service.impl;

import com.photography.portfolio.entity.Kategori;
import com.photography.portfolio.repository.KategoriRepository;
import com.photography.portfolio.service.KategoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KategoriServiceImpl implements KategoriService {

    private final KategoriRepository kategoriRepository;

    @Override
    public List<Kategori> findAll() {
        return kategoriRepository.findAll();
    }

    @Override
    public List<Kategori> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return kategoriRepository.findAll();
        }
        return kategoriRepository.findByNamaKategoriContainingIgnoreCase(keyword);
    }

    @Override
    public Optional<Kategori> findById(Long id) {
        return kategoriRepository.findById(id);
    }

    @Override
    public Kategori save(Kategori kategori) {
        return kategoriRepository.save(kategori);
    }

    @Override
    public void deleteById(Long id) {
        kategoriRepository.deleteById(id);
    }

    @Override
    public long count() {
        return kategoriRepository.count();
    }
}
