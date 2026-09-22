package com.aydsii.tp2.controller;

import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.dto.pedidos.PedidoResponseDTO;
import com.aydsii.tp2.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Ejercicio 5 - Historial de Pedidos", description = "Consulta de pedidos con filtros combinables")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar pedidos con filtros", description = "Permite filtrar pedidos por cliente, categoría, rango de fechas y estado de manera opcional y combinada.")
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> buscarPedidos(
            @RequestParam(required = false) Long clienteId,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaDesde,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaHasta,
            @RequestParam(required = false) String estado) {

        List<PedidoResponseDTO> resultados = pedidoService.buscarPedidos(clienteId, categoria, fechaDesde, fechaHasta, estado);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.success(200, "Consulta realizada correctamente", resultados));
    }
}