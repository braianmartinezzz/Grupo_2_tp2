package com.aydsii.tp2.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
@Schema(description = "Estadísticas de ventas")
public class EstadisticaResponseDTO {

    @Schema(description = "Total facturado por todas las ventas", example = "25000.0")
    private Double totalFacturado;

    @Schema(description = "Cantidad total de ventas realizadas", example = "120")
    private Integer cantidadVentas;

    @Schema(description = "Promedio de facturación por venta", example = "208.33") 
    private Double ticketPromedio;

    @Schema(description = "Venta con el mayor monto facturado")
    private VentaConImporteDTO ventaMayor;

    @Schema(description = "Venta con el menor monto facturado")
    private VentaConImporteDTO ventaMenor;

    @Schema(description = "Producto con mayor cantidad de ventas", example = "Coca Cola")
    private String productoMasVendido;
}