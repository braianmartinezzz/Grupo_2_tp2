package com.aydsii.tp2.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor 
@Schema(description = "Detalle de una venta con el importe total calculado")
public class VentaConDescuentoDTO extends VentaDTO {

    @Schema(description = "Monto de la venta con descuento aplicado", example = "12150.0")
    private double montoConDescuento;

    public VentaConDescuentoDTO(String producto, int cantidad, double precioUnitario, double montoConDescuento) {
        super(producto, cantidad, precioUnitario);
        this.montoConDescuento = montoConDescuento;
    }
}
