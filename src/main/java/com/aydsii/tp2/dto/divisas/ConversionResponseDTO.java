package com.aydsii.tp2.dto.divisas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponseDTO {
    private Double montoOriginal;
    private String monedaOrigen;
    private String monedaDestino;
    private Double tasaCambio;
    private Double montoConvertido;
    private String fecha;
}