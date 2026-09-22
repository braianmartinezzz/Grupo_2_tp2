package com.aydsii.tp2.service;

import com.aydsii.tp2.dto.divisas.ConversionResponseDTO;
import com.aydsii.tp2.dto.divisas.FrankfurterResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DivisasService {

    private final RestClient restClient;

    public DivisasService() {
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
}