package com.aydsii.tp2.dto.ventas;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Resultado de aplicar un descuento a una lista de ventas")
public class DescuentoResponseDTO {

    @Schema(description = "Ventas con su monto con descuento aplicado")
    private List<VentaConDescuentoDTO> ventas;

    @Schema(description = "Suma total de todos los montos con descuento", example = "45600.0")
    private Double totalConDescuento;
}
