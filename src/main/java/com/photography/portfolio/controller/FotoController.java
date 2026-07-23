package com.photography.portfolio.controller;

import com.photography.portfolio.entity.Foto;
import com.photography.portfolio.service.FotoService;
import com.photography.portfolio.service.KameraService;
import com.photography.portfolio.service.KategoriService;
import com.photography.portfolio.service.LokasiService;
import com.photography.portfolio.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * CRUD Foto khusus untuk Admin.
 * Untuk melihat daftar & detail foto (Admin maupun User) lihat FotoViewController.
 */
@Controller
@RequestMapping("/admin/foto")
@RequiredArgsConstructor
public class FotoController {

    private final FotoService fotoService;
    private final KameraService kameraService;
    private final LokasiService lokasiService;
    private final KategoriService kategoriService;
    private final UserService userService;

    private void populateDropdowns(Model model) {
        model.addAttribute("kameraList", kameraService.findAll());
        model.addAttribute("lokasiList", lokasiService.findAll());
        model.addAttribute("kategoriList", kategoriService.findAll());
        model.addAttribute("userList", userService.findAll());
    }

    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("fotoList", fotoService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "foto/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        Foto foto = new Foto();
        foto.setTanggalUpload(LocalDate.now());
        model.addAttribute("foto", foto);
        model.addAttribute("mode", "add");
        populateDropdowns(model);
        return "foto/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return fotoService.findById(id)
                .map(foto -> {
                    model.addAttribute("foto", foto);
                    model.addAttribute("mode", "edit");
                    populateDropdowns(model);
                    return "foto/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Foto tidak ditemukan");
                    return "redirect:/admin/foto/list";
                });
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return fotoService.findById(id)
                .map(foto -> {
                    model.addAttribute("foto", foto);
                    return "foto/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Foto tidak ditemukan");
                    return "redirect:/admin/foto/list";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("foto") Foto foto, BindingResult result,
                        Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("mode", foto.getId() == null ? "add" : "edit");
            populateDropdowns(model);
            return "foto/form";
        }
        fotoService.save(foto);
        redirectAttributes.addFlashAttribute("successMessage", "Data foto berhasil disimpan");
        return "redirect:/admin/foto/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            fotoService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Data foto berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus foto");
        }
        return "redirect:/admin/foto/list";
    }
}
