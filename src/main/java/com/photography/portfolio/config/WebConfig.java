package com.photography.portfolio.config;

import com.photography.portfolio.entity.Kamera;
import com.photography.portfolio.entity.Kategori;
import com.photography.portfolio.entity.Lokasi;
import com.photography.portfolio.entity.User;
import com.photography.portfolio.repository.KameraRepository;
import com.photography.portfolio.repository.KategoriRepository;
import com.photography.portfolio.repository.LokasiRepository;
import com.photography.portfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Mendaftarkan Converter agar dropdown <select> pada form Foto (Kamera, Lokasi,
 * Kategori, User) dapat langsung di-bind menjadi Entity berdasarkan id yang dipilih.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final KameraRepository kameraRepository;
    private final LokasiRepository lokasiRepository;
    private final KategoriRepository kategoriRepository;
    private final UserRepository userRepository;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Kamera>() {
            @Override
            public Kamera convert(String source) {
                if (!StringUtils.hasText(source)) return null;
                return kameraRepository.findById(Long.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, Lokasi>() {
            @Override
            public Lokasi convert(String source) {
                if (!StringUtils.hasText(source)) return null;
                return lokasiRepository.findById(Long.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, Kategori>() {
            @Override
            public Kategori convert(String source) {
                if (!StringUtils.hasText(source)) return null;
                return kategoriRepository.findById(Long.valueOf(source)).orElse(null);
            }
        });

        registry.addConverter(new Converter<String, User>() {
            @Override
            public User convert(String source) {
                if (!StringUtils.hasText(source)) return null;
                return userRepository.findById(Long.valueOf(source)).orElse(null);
            }
        });
    }
}
