package com.aydsii.tp2.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data 
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor 
@Schema(description = "Detalle de una venta con el importe total calculado")
public class VentaConImporteDTO extends VentaDTO {

    @Schema(description = "Importe total de la venta (cantidad x precioUnitario)", example = "13500.0")
    private double importe;

    public VentaConImporteDTO(String producto, int cantidad, double precioUnitario, double importe) {
        super(producto, cantidad, precioUnitario);
        this.importe = importe;
    }
}
