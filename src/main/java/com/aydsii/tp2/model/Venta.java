package com.aydsii.tp2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Venta {
    private String producto;
    private int cantidad;
    private double precioUnitario;
}
