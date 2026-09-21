package com.aydsii.tp2.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa una venta realizada en el sistema")
public class VentaDTO {

    @NotBlank(message = "El producto es obligatorio")
    @Schema(description = "Nombre del producto", example = "Coca Cola")
    private String producto;

    @Min(value = 1, message = "La cantidad no puede ser menor o igual a 0")
    @Schema(description = "Cantidad de productos", example = "3")
    private Integer cantidad;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio no puede ser menor o igual a 0")
    @Schema(description = "Precio unitario del producto", example = "2500.0")
    private Double precioUnitario;
}
