package com.aydsii.tp2.dto;

import jakarta.validation.constraints.*; // De acá vienen las validaciones

public class VentaDTO{

    @NotBlank 
    private String producto;

    @Positive 
    private Integer cantidad;

    @Positive 
    private Double precioUnitario;

    public String getProducto(){return producto;}
    public void setProducto(String producto){this.producto = producto;}

    public Integer getCantidad(){return cantidad;}
    public void setCantidad(Integer cantidad){this.cantidad = cantidad;}

    public Double getPrecioUnitario(){return precioUnitario;}
    public void setPrecioUnitario(Double precioUnitario){this.precioUnitario = precioUnitario;}

}