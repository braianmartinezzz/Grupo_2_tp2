package com.aydsii.tp2.service;

import java.util.List;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.aydsii.tp2.model.Producto;

@Service
public class ProductoService {

    private List<Producto> catalogo;

    public ProductoService() {
        catalogo = new ArrayList<>();
        catalogo.add(new Producto(1L, "Mouse inalámbrico", "Perifericos", 4500.0, 50));
        catalogo.add(new Producto(2L, "Teclado Mecánico", "Perifericos", 25000.0, 30));
        catalogo.add(new Producto(3L, "Monitor 24 pulgadas", "Monitores", 150000.0, 15));
        catalogo.add(new Producto(4L, "Auriculares con micrófono", "Audio", 18500.0, 40));
        catalogo.add(new Producto(5L, "Disco Solido SSD 1TB", "Almacenamiento", 55000.0, 25));
        catalogo.add(new Producto(6L, "Memoria RAM 16GB", "Componentes", 32000.0, 50));
        catalogo.add(new Producto(7L, "Gabinete ATX", "Componentes", 45000.0, 10));
        catalogo.add(new Producto(8L, "Webcam Full HD", "Perifericos", 21000.0, 20));
    }

    public List<Producto> obtenerTodos(){
        return catalogo;
    }

    public List<Producto> productosFiltrados(String categoria, Double precioMin, Double precioMax){

    List<Producto> resultados = new ArrayList<>();

    for (Producto producto : catalogo) {
        
        boolean coincideCategoria = (categoria == null || producto.getCategoria().equalsIgnoreCase(categoria));
        boolean coincidePrecioMin = (precioMin == null || producto.getPrecio() >= precioMin);
        boolean coincidePrecioMax = (precioMax == null || producto.getPrecio() <= precioMax);

        if (coincideCategoria && coincidePrecioMin && coincidePrecioMax) {
            resultados.add(producto);
        }
    }

    return resultados;
    
    }

}
