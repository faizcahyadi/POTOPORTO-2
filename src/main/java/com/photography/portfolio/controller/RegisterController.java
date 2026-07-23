package com.photography.portfolio.controller;

import com.photography.portfolio.dto.RegisterDto;
import com.photography.portfolio.entity.User;
import com.photography.portfolio.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Halaman pendaftaran akun User baru (self-registration).
 * Akun yang terdaftar lewat sini otomatis mendapat role USER
 * (bukan Admin — Admin tidak bisa didaftarkan lewat form publik ini).
 */
@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("registerDto") RegisterDto registerDto,
                            BindingResult result, Model model, RedirectAttributes redirectAttributes) {

        if (userService.findByEmail(registerDto.getEmail()).isPresent()) {
            result.rejectValue("email", "duplicate", "Email sudah terdaftar, silakan gunakan email lain");
        }

        if (registerDto.getPassword() != null && !registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "mismatch", "Konfirmasi password tidak cocok");
        }

        if (result.hasErrors()) {
            return "register";
        }

        User user = new User();
        user.setNama(registerDto.getNama());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        userService.save(user);

        redirectAttributes.addFlashAttribute("registerSuccess", "Pendaftaran berhasil! Silakan login menggunakan email dan password Anda.");
        return "redirect:/login";
    }
}
