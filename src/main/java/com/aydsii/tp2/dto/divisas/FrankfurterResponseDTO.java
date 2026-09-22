package com.aydsii.tp2.dto.divisas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FrankfurterResponseDTO {
    private Double amount;
    private String base;
    private String date;
    private Map<String, Double> rates;
}