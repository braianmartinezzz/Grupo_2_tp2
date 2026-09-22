package com.aydsii.tp2.controller;

import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.dto.ClienteDTO;
import com.aydsii.tp2.model.Cliente;
import com.aydsii.tp2.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Ejercicio 4 - Clientes", description = "Alta de clientes en base de datos MySQL")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Alta simple", description = "Registra un cliente nuevo y permite que la base de datos genere el id.")
    public ResponseEntity<ApiResponse<Cliente>> crearCliente(@RequestBody ClienteDTO clienteDTO) {
        // Endpoint 1: Alta simple sin validaciones de Bean Validation
        Cliente clienteCreado = clienteService.crearCliente(clienteDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Cliente creado con exito", clienteCreado));
    }

    @PostMapping("/validado")
    @Operation(summary = "Alta con validación", description = "Registra un cliente comprobando reglas de formato y unicidad de email.")
    public ResponseEntity<ApiResponse<Object>> crearClienteValidado(@Valid @RequestBody ClienteDTO clienteDTO) {
        // Endpoint 2: Validar si el email ya existe en la base de datos antes del INSERT
        if (clienteService.existeEmail(clienteDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "El email ya est registrado"));
        }

        Cliente clienteCreado = clienteService.crearCliente(clienteDTO);
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Cliente creado con exito", clienteCreado));
    }
}