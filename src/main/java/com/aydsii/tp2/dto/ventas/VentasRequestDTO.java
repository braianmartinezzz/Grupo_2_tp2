package com.aydsii.tp2.dto.ventas;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data 
public class VentasRequestDTO {
    @NotEmpty(message = "La lista de ventas no puede venir vacía")
    @Valid 
    private List<VentaDTO> ventas;
}
