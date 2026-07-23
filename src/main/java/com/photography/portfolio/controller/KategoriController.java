package com.photography.portfolio.controller;

import com.photography.portfolio.entity.Kategori;
import com.photography.portfolio.service.KategoriService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/kategori")
@RequiredArgsConstructor
public class KategoriController {

    private final KategoriService kategoriService;

    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("kategoriList", kategoriService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "kategori/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("kategori", new Kategori());
        model.addAttribute("mode", "add");
        return "kategori/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return kategoriService.findById(id)
                .map(kategori -> {
                    model.addAttribute("kategori", kategori);
                    model.addAttribute("mode", "edit");
                    return "kategori/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Kategori tidak ditemukan");
                    return "redirect:/admin/kategori/list";
                });
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return kategoriService.findById(id)
                .map(kategori -> {
                    model.addAttribute("kategori", kategori);
                    return "kategori/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Kategori tidak ditemukan");
                    return "redirect:/admin/kategori/list";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("kategori") Kategori kategori, BindingResult result,
                        Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("mode", kategori.getId() == null ? "add" : "edit");
            return "kategori/form";
        }
        kategoriService.save(kategori);
        redirectAttributes.addFlashAttribute("successMessage", "Data kategori berhasil disimpan");
        return "redirect:/admin/kategori/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            kategoriService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Data kategori berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus kategori, kemungkinan masih dipakai data foto");
        }
        return "redirect:/admin/kategori/list";
    }
}
