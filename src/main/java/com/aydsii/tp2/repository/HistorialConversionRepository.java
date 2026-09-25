package com.aydsii.tp2.repository;

import com.aydsii.tp2.model.HistorialConversion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialConversionRepository extends JpaRepository<HistorialConversion, Long> {
    
    // Spring Data JPA procesa automáticamente esta firma para buscar por origen/destino y ordenar DESC (más reciente a más antigua)
    List<HistorialConversion> findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(String monedaOrigen, String monedaDestino);
}

