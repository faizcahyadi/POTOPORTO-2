package com.photography.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDto {
    private long totalUser;
    private long totalFoto;
    private long totalKamera;
    private long totalLokasi;
    private long totalKategori;
}
