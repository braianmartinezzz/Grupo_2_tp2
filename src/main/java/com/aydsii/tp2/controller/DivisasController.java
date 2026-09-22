package com.aydsii.tp2.controller;

import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.dto.divisas.ConversionResponseDTO;
import com.aydsii.tp2.service.DivisasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/divisas")
@Tag(name = "Ejercicio 3 - Divisas", description = "Consumo de API externa para conversión de monedas")
public class DivisasController {

    private final DivisasService divisasService;

    public DivisasController(DivisasService divisasService) {
        this.divisasService = divisasService;
    }

    // Endpoint 1: GET /api/divisas/convertir

    @GetMapping("/convertir")
    @Operation(summary = "Convertir divisas", description = "Convierte un monto de una moneda a otra utilizando la API externa Frankfurter.")
    public ResponseEntity<ApiResponse<ConversionResponseDTO>> convertir(
            @RequestParam Double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        // 1. Validaciones requeridas de los datos recibidos
        if (monto == null || monto <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "El monto debe ser mayor que 0"));
        }
        if (origen == null || origen.trim().length() != 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "La moneda de origen debe tener exactamente 3 letras"));
        }
        if (destino == null || destino.trim().length() != 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "La moneda de destino debe tener exactamente 3 letras"));
        }

        try {
            // 2. Procesar la llamada a la API externa a través del servicio
            ConversionResponseDTO resultado = divisasService.convertir(monto, origen.toUpperCase(), destino.toUpperCase());
            return ResponseEntity.ok(ApiResponse.success(200, "Conversión realizada con exito", resultado));
            
        } catch (ResponseStatusException e) {
            // 3. Manejo de errores: Captura las excepciones 400 (Bad Request) o 502 (Bad Gateway) 
            // lanzadas por el Service y las empaqueta respetando el formato de respuesta estándar[cite: 9]
            return ResponseEntity.status(e.getStatusCode())
                    .body(ApiResponse.error(e.getStatusCode().value(), e.getReason()));
        }
    }
}