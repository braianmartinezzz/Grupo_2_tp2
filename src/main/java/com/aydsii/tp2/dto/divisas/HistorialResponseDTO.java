package com.aydsii.tp2.dto.divisas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class HistorialResponseDTO {
    private LocalDateTime fecha;
    private Double tasaCambio;
}