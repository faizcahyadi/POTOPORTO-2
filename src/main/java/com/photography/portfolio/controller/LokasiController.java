package com.photography.portfolio.controller;

import com.photography.portfolio.entity.Lokasi;
import com.photography.portfolio.service.LokasiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/lokasi")
@RequiredArgsConstructor
public class LokasiController {

    private final LokasiService lokasiService;

    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("lokasiList", lokasiService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "lokasi/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("lokasi", new Lokasi());
        model.addAttribute("mode", "add");
        return "lokasi/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return lokasiService.findById(id)
                .map(lokasi -> {
                    model.addAttribute("lokasi", lokasi);
                    model.addAttribute("mode", "edit");
                    return "lokasi/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Lokasi tidak ditemukan");
                    return "redirect:/admin/lokasi/list";
                });
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return lokasiService.findById(id)
                .map(lokasi -> {
                    model.addAttribute("lokasi", lokasi);
                    return "lokasi/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Lokasi tidak ditemukan");
                    return "redirect:/admin/lokasi/list";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("lokasi") Lokasi lokasi, BindingResult result,
                        Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("mode", lokasi.getId() == null ? "add" : "edit");
            return "lokasi/form";
        }
        lokasiService.save(lokasi);
        redirectAttributes.addFlashAttribute("successMessage", "Data lokasi berhasil disimpan");
        return "redirect:/admin/lokasi/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            lokasiService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Data lokasi berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus lokasi, kemungkinan masih dipakai data foto");
        }
        return "redirect:/admin/lokasi/list";
    }
}
