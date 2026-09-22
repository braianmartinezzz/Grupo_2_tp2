package com.aydsii.tp2.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import java.util.stream.Stream;
import org.springframework.stereotype.Service;

import com.aydsii.tp2.model.Producto;

import jakarta.annotation.PostConstruct;

@Service 
public class CatalogoService {
    
    private List<Producto> productos = new ArrayList<>();

    @PostConstruct
    public void inicializarDatos() {
        // Se utiliza el sufijo "L" para los Long y ".0" para los Double
        productos.add(new Producto(1L, "Ojotas", "Indumentaria", 20000.0, 10));
        productos.add(new Producto(2L, "Remera", "Indumentaria", 15000.0, 20));
        productos.add(new Producto(3L, "Teclado", "Perifericos", 25000.0, 8));
        productos.add(new Producto(4L, "Mouse", "Perifericos", 12000.0, 15));
        productos.add(new Producto(5L, "Monitor", "Perifericos", 150000.0, 5));
        productos.add(new Producto(6L, "Pantalon", "Indumentaria", 35000.0, 12));
        productos.add(new Producto(7L, "Zapatillas", "Calzado", 80000.0, 7));
        productos.add(new Producto(8L, "Gorra", "Accesorios", 10000.0, 25));
    }
    
    // GET /api/catalogo
    public List<Producto> obtenerTodos() {
        return productos;
    }

    // GET /api/catalogo/buscar
    public List<Producto> buscarProductos(String categoria, Double precioMin, Double precioMax) {
        Stream<Producto> streamProductos = productos.stream();

        // Se aplican los filtros utilizando la API de Streams
        if (categoria != null && !categoria.isBlank()) {
            streamProductos = streamProductos.filter(p -> p.getCategoria().equalsIgnoreCase(categoria));
        }
        if (precioMin != null) {
            streamProductos = streamProductos.filter(p -> p.getPrecio() >= precioMin);
        }
        if (precioMax != null) {
            streamProductos = streamProductos.filter(p -> p.getPrecio() <= precioMax);
        }

        return streamProductos.collect(Collectors.toList());
    }

    // GET /api/catalogo/ordenar
    public List<Producto> ordenarProductos(String criterio, String orden) {
        Comparator<Producto> comparador;

        // El ordenamiento se realiza utilizando Streams y Comparator[cite: 1]
        if ("nombre".equalsIgnoreCase(criterio)) {
            comparador = Comparator.comparing(Producto::getNombre);
        } else {
            comparador = Comparator.comparing(Producto::getPrecio); 
        }

        if ("desc".equalsIgnoreCase(orden)) {
            comparador = comparador.reversed();
        }

        return productos.stream()
                .sorted(comparador)
                .collect(Collectors.toList());
    }

    // POST /api/catalogo
    public Producto agregarProducto(Producto producto) {
        // Genera un ID autoincremental buscando el valor más alto en la lista
        Long nuevoId = productos.stream()
                .mapToLong(Producto::getId)
                .max()
                .orElse(0L) + 1L;
        
        producto.setId(nuevoId);
        productos.add(producto);
        return producto;
    }

    // PUT /api/catalogo/{id}/stock
    public Optional<Producto> modificarStock(Long id, Integer cantidad) {
        Optional<Producto> productoBuscado = productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();

        if (productoBuscado.isPresent()) {
            Producto producto = productoBuscado.get();
            int nuevoStock = producto.getStock() + cantidad;
            
            // Verifica que la modificación no provoque un stock negativo[cite: 1]
            if (nuevoStock < 0) {
                throw new IllegalArgumentException("La modificación provocaría un stock negativo");
            }
            
            producto.setStock(nuevoStock);
        }
        
        return productoBuscado;
    }

    // DELETE /api/catalogo/{id}
    public boolean eliminarProducto(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}
