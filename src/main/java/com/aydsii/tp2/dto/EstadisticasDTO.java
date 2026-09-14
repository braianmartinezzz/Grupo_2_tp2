package com.aydsii.tp2.dto;

import lombok.Data;

@Data
public class EstadisticasDTO {
    
    private Double totalFacturado;

    private Integer cantidadVentas;

    private Double ticketPromedio;

    private VentaDTO ventaMayor;

    private VentaDTO ventaMenor;

    private String productoMasVendido;

}
