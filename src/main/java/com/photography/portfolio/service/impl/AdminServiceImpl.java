package com.photography.portfolio.service.impl;

import com.photography.portfolio.entity.Admin;
import com.photography.portfolio.repository.AdminRepository;
import com.photography.portfolio.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    public Admin save(Admin admin) {
        if (admin.getId() == null) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        } else {
            Optional<Admin> existing = adminRepository.findById(admin.getId());
            if (existing.isPresent() && !existing.get().getPassword().equals(admin.getPassword())) {
                admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            }
        }
        return adminRepository.save(admin);
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
}
