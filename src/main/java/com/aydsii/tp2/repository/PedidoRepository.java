package com.aydsii.tp2.repository;

import com.aydsii.tp2.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT DISTINCT p FROM Pedido p " +
           "JOIN p.cliente c " +
           "JOIN p.detalles d " +
           "JOIN d.producto pr " +
           "JOIN pr.categoria cat " +
           "WHERE (:clienteId IS NULL OR c.id = :clienteId) " +
           "AND (:categoria IS NULL OR cat.nombre = :categoria) " +
           "AND (:fechaDesde IS NULL OR p.fechaPedido >= :fechaDesde) " +
           "AND (:fechaHasta IS NULL OR p.fechaPedido <= :fechaHasta) " +
           "AND (:estado IS NULL OR p.estado = :estado)")
    List<Pedido> buscarPedidosConFiltros(
            @Param("clienteId") Long clienteId,
            @Param("categoria") String categoria,
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta,
            @Param("estado") String estado
    );
}