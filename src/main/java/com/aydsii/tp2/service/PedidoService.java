package com.aydsii.tp2.service;

import com.aydsii.tp2.dto.pedidos.DetallePedidoResponseDTO;
import com.aydsii.tp2.dto.pedidos.PedidoResponseDTO;
import com.aydsii.tp2.model.Pedido;
import com.aydsii.tp2.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponseDTO> buscarPedidos(Long clienteId, String categoria, LocalDate fechaDesde, LocalDate fechaHasta, String estado) {
        List<Pedido> pedidos = pedidoRepository.buscarPedidosConFiltros(clienteId, categoria, fechaDesde, fechaHasta, estado);

        return pedidos.stream().map(pedido -> {
            PedidoResponseDTO dto = new PedidoResponseDTO();
            dto.setPedidoId(pedido.getId());
            dto.setCliente(pedido.getCliente().getNombre() + " " + pedido.getCliente().getApellido());
            dto.setFecha(pedido.getFechaPedido());
            dto.setEstado(pedido.getEstado());

            // Mapear los detalles y calcular subtotales
            List<DetallePedidoResponseDTO> detallesDTO = pedido.getDetalles().stream().map(det -> {
                DetallePedidoResponseDTO detDto = new DetallePedidoResponseDTO();
                detDto.setNombre(det.getProducto().getNombre());
                detDto.setCategoria(det.getProducto().getCategoria() != null ? det.getProducto().getCategoria().getNombre() : "Sin categoría");
                detDto.setCantidad(det.getCantidad());
                detDto.setSubtotal(det.getCantidad() * det.getPrecioUnitario());
                return detDto;
            }).collect(Collectors.toList());

            dto.setProductos(detallesDTO);

            // Calcular el total del pedido sumando los subtotales
            double total = detallesDTO.stream().mapToDouble(DetallePedidoResponseDTO::getSubtotal).sum();
            dto.setTotalPedido(total);

            return dto;
        }).collect(Collectors.toList());
    }
}