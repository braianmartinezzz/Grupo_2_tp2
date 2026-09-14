package com.aydsii.tp2.dto;

import jakarta.validation.constraints.*; // De acá vienen las validaciones
import lombok.Data;

@Data
public class VentaDTO{

    @NotBlank 
    private String producto;

    @Positive 
    private Integer cantidad;

    @Positive 
    private Double precioUnitario;

}