package com.aydsii.tp2;

import java.util.*;

import com.aydsii.tp2.dto.ventas.VentaDTO;

import jakarta.annotation.PostConstruct;

public class Storage {
    private final Map<String, VentaDTO> ventas = new LinkedHashMap<>();

    @PostConstruct 
    public void cargarDatosIniciales(){
        guardar(new VentaDTO("Producto A", 10, 5.0));
        guardar(new VentaDTO("Producto B", 5, 10.0));
        guardar(new VentaDTO("Producto C", 2, 20.0));
        guardar(new VentaDTO("Producto D", 1, 50.0));
        guardar(new VentaDTO("Producto E", 3, 15.0));
    }

    public VentaDTO guardar(VentaDTO venta){
        ventas.put(venta.getProducto(), venta);
        return venta;
    }

    public List<VentaDTO> obtenerTodasLasVentas(){
        return new ArrayList<>(ventas.values());
    }
}
