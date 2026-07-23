package com.photography.portfolio.service.impl;

import com.photography.portfolio.entity.Lokasi;
import com.photography.portfolio.repository.LokasiRepository;
import com.photography.portfolio.service.LokasiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LokasiServiceImpl implements LokasiService {

    private final LokasiRepository lokasiRepository;

    @Override
    public List<Lokasi> findAll() {
        return lokasiRepository.findAll();
    }

    @Override
    public List<Lokasi> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return lokasiRepository.findAll();
        }
        return lokasiRepository.findByNamaLokasiContainingIgnoreCaseOrKotaContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public Optional<Lokasi> findById(Long id) {
        return lokasiRepository.findById(id);
    }

    @Override
    public Lokasi save(Lokasi lokasi) {
        return lokasiRepository.save(lokasi);
    }

    @Override
    public void deleteById(Long id) {
        lokasiRepository.deleteById(id);
    }

    @Override
    public long count() {
        return lokasiRepository.count();
    }
}
