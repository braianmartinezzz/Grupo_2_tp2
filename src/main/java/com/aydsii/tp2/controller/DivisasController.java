package com.aydsii.tp2.controller;

import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.dto.divisas.ConversionResponseDTO;
import com.aydsii.tp2.dto.divisas.HistorialResponseDTO;
import com.aydsii.tp2.service.DivisasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/divisas")
@Tag(name = "Ejercicio 3 y 6 - Divisas", description = "Consumo de API externa para conversión de monedas y persistencia del historial")
public class DivisasController {

    private final DivisasService divisasService;

    public DivisasController(DivisasService divisasService) {
        this.divisasService = divisasService;
    }

    // ==========================================
    // Endpoint Ejercicio 3
    // ==========================================

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
            // 3. Manejo de errores
            return ResponseEntity.status(e.getStatusCode())
                    .body(ApiResponse.error(e.getStatusCode().value(), e.getReason()));
        }
    }

    // ==========================================
    // Endpoints Ejercicio 6
    // ==========================================

    @PostMapping("/consultar")
    @Operation(summary = "Consultar cotización y guardar", description = "Llama a Frankfurter y guarda el historial en la base de datos.")
    public ResponseEntity<ApiResponse<ConversionResponseDTO>> consultar(
            @RequestParam Double monto,
            @RequestParam String origen,
            @RequestParam String destino) {

        // Mismas validaciones que en el Ejercicio 3
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
            // Llama al nuevo método que hace la conversión y además guarda en la tabla de MySQL
            ConversionResponseDTO resultado = divisasService.consultarYGuardar(monto, origen.toUpperCase(), destino.toUpperCase());
            return ResponseEntity.ok(ApiResponse.success(200, "Consulta guardada con éxito", resultado));
            
        } catch (ResponseStatusException e) {
            // Captura errores de red al igual que el endpoint de convertir
            return ResponseEntity.status(e.getStatusCode())
                    .body(ApiResponse.error(e.getStatusCode().value(), e.getReason()));
        }
    }

    @GetMapping("/historial")
    @Operation(summary = "Ver historial de cotizaciones", description = "Devuelve el historial ordenado de más reciente a más antiguo para un par de monedas.")
    public ResponseEntity<ApiResponse<List<HistorialResponseDTO>>> verHistorial(
            @RequestParam String origen,
            @RequestParam String destino) {

        // Validamos solo las monedas, ya que el historial no requiere monto
        if (origen == null || origen.trim().length() != 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "La moneda de origen debe tener exactamente 3 letras"));
        }
        if (destino == null || destino.trim().length() != 3) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "La moneda de destino debe tener exactamente 3 letras"));
        }

        // Obtiene la lista desde la base de datos ordenada descendentemente por fecha
        List<HistorialResponseDTO> historial = divisasService.obtenerHistorial(origen.toUpperCase(), destino.toUpperCase());
        
        return ResponseEntity.ok(ApiResponse.success(200, "Historial recuperado correctamente", historial));
    }
}