package com.photography.portfolio.controller;

import com.photography.portfolio.dto.DashboardStatsDto;
import com.photography.portfolio.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final UserService userService;
    private final FotoService fotoService;
    private final KameraService kameraService;
    private final LokasiService lokasiService;
    private final KategoriService kategoriService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardStatsDto stats = new DashboardStatsDto(
                userService.count(),
                fotoService.count(),
                kameraService.count(),
                lokasiService.count(),
                kategoriService.count()
        );
        model.addAttribute("stats", stats);
        return "dashboard";
    }
}
