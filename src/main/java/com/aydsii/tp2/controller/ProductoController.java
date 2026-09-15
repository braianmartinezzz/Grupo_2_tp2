package com.aydsii.tp2.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.model.Producto;
import com.aydsii.tp2.service.ProductoService;

@RestController
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping("/api/catalogo")
    public ApiResponse<List<Producto>> obtenerTodos(){
        return new ApiResponse(200, "Mensaje recibido con éxito", productoService.obtenerTodos());
    }

    @GetMapping("/api/catalogo/buscar")
    public ApiResponse<List<Producto>> buscarProductos(@RequestParam(required = false) String categoria, @RequestParam(required = false) Double precioMin, @RequestParam(required = false) Double precioMax){
        return new ApiResponse<>(200, "Búsqueda exitosa", productoService.productosFiltrados(categoria, precioMin, precioMax));
    }

}
