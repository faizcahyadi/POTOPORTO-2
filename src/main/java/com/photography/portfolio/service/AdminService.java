package com.photography.portfolio.service;

import com.photography.portfolio.entity.Admin;

import java.util.List;
import java.util.Optional;

public interface AdminService {
    List<Admin> findAll();
    Optional<Admin> findById(Long id);
    Admin save(Admin admin);
    void deleteById(Long id);
    Optional<Admin> findByUsername(String username);
}
