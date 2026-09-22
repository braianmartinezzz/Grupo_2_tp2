package com.aydsii.tp2.dto.pedidos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetallePedidoResponseDTO {
    private String nombre;
    private String categoria;
    private Integer cantidad;
    private Double subtotal;
}