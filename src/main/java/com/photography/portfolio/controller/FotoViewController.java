package com.photography.portfolio.controller;

import com.photography.portfolio.service.FotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Halaman untuk melihat daftar & detail foto.
 * Bisa diakses oleh role ADMIN maupun USER (read-only untuk USER).
 */
@Controller
@RequestMapping("/foto")
@RequiredArgsConstructor
public class FotoViewController {

    private final FotoService fotoService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("fotoList", fotoService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "userview/foto-list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return fotoService.findById(id)
                .map(foto -> {
                    model.addAttribute("foto", foto);
                    return "userview/foto-detail";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Foto tidak ditemukan");
                    return "redirect:/foto/list";
                });
    }
}
