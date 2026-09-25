package com.aydsii.tp2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_conversiones")
@Getter
@Setter
public class HistorialConversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moneda_origen", nullable = false, length = 10)
    private String monedaOrigen;

    @Column(name = "moneda_destino", nullable = false, length = 10)
    private String monedaDestino;

    @Column(nullable = false)
    private Double monto;

    @Column(name = "monto_convertido", nullable = false)
    private Double montoConvertido;

    @Column(nullable = false)
    private Double tasa;

    @Column(name = "fecha_consulta", nullable = false)
    private LocalDateTime fechaConsulta;
}