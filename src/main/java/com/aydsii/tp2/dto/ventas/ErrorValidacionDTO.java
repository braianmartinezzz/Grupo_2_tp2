package com.aydsii.tp2.dto.ventas;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Schema(description = "Detalle de un error de validación en un elemento de la lista")
public class ErrorValidacionDTO {

    @Schema(description = "Posición del elemento dentro de la lista", example = "2")
    private int posicion;

    @Schema(description = "Nombre del campo que no cumplió la validación", example = "cantidad")
    private String campo;

    @Schema(description = "Motivo por el cual falló la validación", example = "La cantidad no puede ser menor o igual a 0")
    private String motivo;
}
