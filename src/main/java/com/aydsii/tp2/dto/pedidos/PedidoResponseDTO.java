package com.aydsii.tp2.dto.pedidos;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PedidoResponseDTO {
    private Long pedidoId;
    private String cliente;
    private LocalDate fecha;
    private String estado;
    private Double totalPedido;
    private List<DetallePedidoResponseDTO> productos;
}