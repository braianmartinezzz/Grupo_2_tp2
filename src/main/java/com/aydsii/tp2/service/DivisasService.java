package com.aydsii.tp2.service;

import com.aydsii.tp2.dto.divisas.ConversionResponseDTO;
import com.aydsii.tp2.dto.divisas.FrankfurterResponseDTO;
import com.aydsii.tp2.dto.divisas.HistorialResponseDTO;
import com.aydsii.tp2.model.HistorialConversion;
import com.aydsii.tp2.repository.HistorialConversionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DivisasService {

    private final RestClient restClient;
    private final HistorialConversionRepository historialRepository;

    public DivisasService(HistorialConversionRepository historialRepository) {
        this.historialRepository = historialRepository;
        // Configuramos el RestClient como indica la consigna técnica
        this.restClient = RestClient.builder()
                .defaultHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                .defaultHeader("Accept", "application/json")
                .build();
    }

    public ConversionResponseDTO convertir(Double monto, String origen, String destino) {
        String url = "https://api.frankfurter.app/latest?amount={monto}&from={origen}&to={destino}";

        try {
            FrankfurterResponseDTO externaResponse = restClient.get()
                    .uri(url, monto, origen, destino)
                    .retrieve()
                    .body(FrankfurterResponseDTO.class);

            if (externaResponse == null || externaResponse.getRates() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Respuesta vacía del servicio externo");
            }

            Double montoConvertido = externaResponse.getRates().get(destino);
            Double tasaCambio = montoConvertido / monto;

            return new ConversionResponseDTO(
                    monto,
                    origen,
                    destino,
                    tasaCambio,
                    montoConvertido,
                    externaResponse.getDate()
            );

        } catch (HttpClientErrorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Moneda no soportada o datos inválidos en la API externa");
        } catch (Exception e) {
            // Este catch general atrapará tu UnknownContentTypeException causado por el firewall de red
            throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "No se pudo obtener información del servicio externo (Bad Gateway)");
        }
    }

    public ConversionResponseDTO consultarYGuardar(Double monto, String origen, String destino) {
        // 1. Reutiliza el método del Ejercicio 3 para llamar a la API y calcular[cite: 6]
        ConversionResponseDTO conversion = convertir(monto, origen, destino);

        // 2. Guardar en base de datos en la tabla historial_conversiones[cite: 6]
        HistorialConversion historial = new HistorialConversion();
        historial.setMonedaOrigen(origen);
        historial.setMonedaDestino(destino);
        historial.setMonto(monto);
        historial.setMontoConvertido(conversion.getMontoConvertido());
        historial.setTasa(conversion.getTasaCambio());
        historial.setFechaConsulta(LocalDateTime.now());
        
        historialRepository.save(historial);

        // 3. Devolver la información al cliente[cite: 6]
        return conversion;
    }

    public List<HistorialResponseDTO> obtenerHistorial(String origen, String destino) {
        // Recuperar de la base de datos y ordenar por fecha (más reciente a más antigua)[cite: 6]
        List<HistorialConversion> registros = historialRepository.findByMonedaOrigenAndMonedaDestinoOrderByFechaConsultaDesc(origen, destino);
        
        // Mapear la entidad al DTO de respuesta que solo contiene fecha y tasa[cite: 6]
        return registros.stream()
                .map(reg -> new HistorialResponseDTO(reg.getFechaConsulta(), reg.getTasa()))
                .toList();
    }
}