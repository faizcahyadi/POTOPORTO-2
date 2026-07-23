package com.photography.portfolio.service.impl;

import com.photography.portfolio.entity.Foto;
import com.photography.portfolio.repository.FotoRepository;
import com.photography.portfolio.service.FotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FotoServiceImpl implements FotoService {

    private final FotoRepository fotoRepository;

    @Override
    public List<Foto> findAll() {
        return fotoRepository.findAll();
    }

    @Override
    public List<Foto> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return fotoRepository.findAll();
        }
        return fotoRepository.findByJudulContainingIgnoreCase(keyword);
    }

    @Override
    public Optional<Foto> findById(Long id) {
        return fotoRepository.findById(id);
    }

    @Override
    public Foto save(Foto foto) {
        return fotoRepository.save(foto);
    }

    @Override
    public void deleteById(Long id) {
        fotoRepository.deleteById(id);
    }

    @Override
    public long count() {
        return fotoRepository.count();
    }
}
