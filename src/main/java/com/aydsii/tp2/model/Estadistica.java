package com.aydsii.tp2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor
@AllArgsConstructor
public class Estadistica {
    private double totalFacturado;
    private int cantidadVentas;
    private double ticketPromedio;
    private Venta ventaMayor;
    private Venta ventaMenor;
    private String productoMasVendido;
}
