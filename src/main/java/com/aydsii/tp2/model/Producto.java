package com.aydsii.tp2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor 
@NoArgsConstructor 
public class Producto {
    private Long id;
    private String nombre;
    private String categoria;
    private Double precio;
    private Integer stock;
}
