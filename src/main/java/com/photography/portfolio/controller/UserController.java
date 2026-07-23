package com.photography.portfolio.controller;

import com.photography.portfolio.entity.User;
import com.photography.portfolio.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping({"", "/list"})
    public String list(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("users", userService.search(keyword));
        model.addAttribute("keyword", keyword);
        return "user/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("mode", "add");
        return "user/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return userService.findById(id)
                .map(user -> {
                    user.setPassword("");
                    model.addAttribute("user", user);
                    model.addAttribute("mode", "edit");
                    return "user/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "User tidak ditemukan");
                    return "redirect:/admin/user/list";
                });
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return userService.findById(id)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "user/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "User tidak ditemukan");
                    return "redirect:/admin/user/list";
                });
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("user") User user, BindingResult result,
                        Model model, RedirectAttributes redirectAttributes) {
        boolean isAdd = user.getId() == null;
        if (isAdd && (user.getPassword() == null || user.getPassword().isBlank())) {
            result.rejectValue("password", "NotBlank", "Password tidak boleh kosong");
        }
        if (result.hasErrors()) {
            model.addAttribute("mode", isAdd ? "add" : "edit");
            return "user/form";
        }
        if (!isAdd && (user.getPassword() == null || user.getPassword().isBlank())) {
            userService.findById(user.getId()).ifPresent(existing -> user.setPassword(existing.getPassword()));
        }
        userService.save(user);
        redirectAttributes.addFlashAttribute("successMessage", "Data user berhasil disimpan");
        return "redirect:/admin/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Data user berhasil dihapus");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menghapus user, kemungkinan masih dipakai data foto");
        }
        return "redirect:/admin/user/list";
    }
}
