package com.photography.portfolio.service.impl;

import com.photography.portfolio.entity.Kamera;
import com.photography.portfolio.repository.KameraRepository;
import com.photography.portfolio.service.KameraService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KameraServiceImpl implements KameraService {

    private final KameraRepository kameraRepository;

    @Override
    public List<Kamera> findAll() {
        return kameraRepository.findAll();
    }

    @Override
    public List<Kamera> search(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return kameraRepository.findAll();
        }
        return kameraRepository.findByNamaKameraContainingIgnoreCaseOrMerkContainingIgnoreCase(keyword, keyword);
    }

    @Override
    public Optional<Kamera> findById(Long id) {
        return kameraRepository.findById(id);
    }

    @Override
    public Kamera save(Kamera kamera) {
        return kameraRepository.save(kamera);
    }

    @Override
    public void deleteById(Long id) {
        kameraRepository.deleteById(id);
    }

    @Override
    public long count() {
        return kameraRepository.count();
    }
}
