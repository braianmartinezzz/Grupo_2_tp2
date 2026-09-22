package com.aydsii.tp2.controller;

import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.model.Producto;
import com.aydsii.tp2.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/catalogo")
@Tag (name = "Ejercicio 2 - Catalogo", description = "Gestión y búsqueda de productos en memoria")
public class CatalogoController {

    private final CatalogoService catalogoService;

    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    @Operation(summary = "Obtener todos los productos")
    public ResponseEntity<ApiResponse<List<Producto>>> obtenerTodos() {
        List<Producto> productos = catalogoService.obtenerTodos();
        return ResponseEntity.ok(ApiResponse.success(200, "Operacion realizada con exito", productos));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Buscar y filtrar productos por categoría y rango de precio")
    public ResponseEntity<ApiResponse<List<Producto>>> buscarProductos(
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Double precioMin,
            @RequestParam(required = false) Double precioMax) {
        
        List<Producto> resultados = catalogoService.buscarProductos(categoria, precioMin, precioMax);
        return ResponseEntity.ok(ApiResponse.success(200, "Busqueda realizada con exito", resultados));
    }

    @GetMapping("/ordenar")
    @Operation(summary = "Ordenar productos por precio o nombre")
    public ResponseEntity<ApiResponse<List<Producto>>> ordenarProductos(
            @RequestParam(defaultValue = "precio") String criterio,
            @RequestParam(defaultValue = "asc") String orden) {
        
        List<Producto> resultados = catalogoService.ordenarProductos(criterio, orden);
        return ResponseEntity.ok(ApiResponse.success(200, "Ordenamiento exitoso", resultados));
    }

    @PostMapping
    @Operation(summary = "Agregar un nuevo producto al catálogo")
    public ResponseEntity<ApiResponse<Producto>> agregarProducto(@RequestBody Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "El nombre no puede estar vacio"));
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "El precio debe ser mayor a 0"));
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, "El stock debe ser mayor o igual a 0"));
        }
        
        Producto nuevoProducto = catalogoService.agregarProducto(producto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "Producto creado exitosamente", nuevoProducto));
    }

    @PutMapping("/{id}/stock")
    @Operation(summary = "Modificar el stock de un producto existente")
    public ResponseEntity<ApiResponse<Producto>> modificarStock(
            @PathVariable Long id, 
            @RequestParam Integer cantidad) {
        try {
            Optional<Producto> productoActualizado = catalogoService.modificarStock(id, cantidad);
            
            if (productoActualizado.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(200, "Stock actualizado", productoActualizado.get()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Producto no encontrado"));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(400, e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto del catálogo")
    public ResponseEntity<ApiResponse<Void>> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = catalogoService.eliminarProducto(id);
        
        if (eliminado) {
            return ResponseEntity.ok(ApiResponse.success(200, "Producto eliminado exitosamente", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Producto no encontrado"));
        }
    }
}