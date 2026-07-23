package com.photography.portfolio.config;

import com.photography.portfolio.entity.Admin;
import com.photography.portfolio.entity.User;
import com.photography.portfolio.repository.AdminRepository;
import com.photography.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Login menggunakan satu form untuk 2 tabel berbeda:
 * - Tabel admin dicek berdasarkan kolom username -> role ROLE_ADMIN
 * - Tabel users dicek berdasarkan kolom email -> role ROLE_USER
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            Admin a = admin.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(a.getUsername())
                    .password(a.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        }

        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            User u = user.get();
            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getEmail())
                    .password(u.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_USER")))
                    .build();
        }

        throw new UsernameNotFoundException("User dengan username/email '" + username + "' tidak ditemukan");
    }
}
