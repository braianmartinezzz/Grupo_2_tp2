package com.aydsii.tp2.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aydsii.tp2.dto.ventas.DescuentoResponseDTO;
import com.aydsii.tp2.dto.ventas.EstadisticaResponseDTO;
import com.aydsii.tp2.dto.ventas.VentasRequestDTO;
import com.aydsii.tp2.service.VentasService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;

@Validated 
@RestController 
@RequestMapping("/api/ventas")
@Tag (name = "Ejercicio 1 - Ventas", description = "Operaciones relacionadas con ventas (ver estadisticas, aplicar descuento)")
public class VentasController {

    private VentasService ventasService;

    public VentasController(VentasService ventasService) {
        this.ventasService = ventasService;
    }


    // Endpoint 1: POST
    @PostMapping("/estadisticas")
    @Operation (
        summary = "Obtener estadísticas de ventas",
        description = "Recibe una lista de ventas y devuelve total facturado, cantidad de ventas, promedio de precio, venta más alta, venta más baja y producto más vendido."
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Estadísticas de ventas devueltas correctamente"
        ),
        @ApiResponse (
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<com.aydsii.tp2.dto.ApiResponse<EstadisticaResponseDTO>> getEstadisticas(@Valid @RequestBody VentasRequestDTO ventasDTO){
        EstadisticaResponseDTO estadistica = ventasService.getEstadisticas(ventasDTO.getVentas());
        return ResponseEntity.ok(com.aydsii.tp2.dto.ApiResponse.success(200, "Estadísticas calculadas correctamente", estadistica));
    }


    // Endpoint 2: POST

    @PostMapping("/aplicar-descuento")
    @Operation (
        summary = "Aplicar descuento a ventas",
        description = "Recibe una lista de ventas y un porcentaje de descuento, y devuelve las ventas con el descuento aplicado."
    )
    @ApiResponses ({
        @ApiResponse (
            responseCode = "200",
            description = "Ventas con descuento devueltas correctamente"
        ),
        @ApiResponse (
            responseCode = "400",
            description = "Datos inválidos"
        )
    })
    public ResponseEntity<com.aydsii.tp2.dto.ApiResponse<DescuentoResponseDTO>> aplicarDescuento(
            @Valid @RequestBody VentasRequestDTO ventasDTO, 
            @RequestParam 
            @Parameter (description = "Porcentaje de descuento a aplicar (0 a 100)", example = "10")
            @DecimalMin(value = "0.0", message = "El porcentaje no puede ser menor que 0")
            @DecimalMax(value = "100.0", message = "El porcentaje no puede ser mayor que 100")
            double porcentaje){
        DescuentoResponseDTO resultado = ventasService.aplicarDescuento(ventasDTO.getVentas(), porcentaje);
        return ResponseEntity.ok(com.aydsii.tp2.dto.ApiResponse.success(200, "Descuento aplicado correctamente", resultado));
    }
}
