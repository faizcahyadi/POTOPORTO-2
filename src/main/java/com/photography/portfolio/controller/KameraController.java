package com.photography.portfolio.controller;

import com.photography.portfolio.entity.Kamera;
import com.photography.portfolio.service.KameraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/kamera")
@RequiredArgsConstructor
public class KameraController {

    private final KameraService kameraService;

    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("kameraList", kameraService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "kamera/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("kamera", new Kamera());
        model.addAttribute("mode", "add");
        return "kamera/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return kameraService.findById(id)
                .map(kamera -> {
                    model.addAttribute("kamera", kamera);
                    model.addAttribute("mode", "edit");
                    return "kamera/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Kamera tidak ditemukan");
                    return "redirect:/admin/kamera/list";
                });
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return kameraService.findById(id)
                .map(kamera -> {
                    model.addAttribute("kamera", kamera);
                    return "kamera/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Kamera tidak ditemukan");
                    return "redirect:/admin/kamera/list";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("kamera") Kamera kamera, BindingResult result,
                        Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("mode", kamera.getId() == null ? "add" : "edit");
            return "kamera/form";
        }
        kameraService.save(kamera);
        redirectAttributes.addFlashAttribute("successMessage", "Data kamera berhasil disimpan");
        return "redirect:/admin/kamera/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            kameraService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Data kamera berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus kamera, kemungkinan masih dipakai data foto");
        }
        return "redirect:/admin/kamera/list";
    }
}
