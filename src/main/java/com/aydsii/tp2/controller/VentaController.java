package com.aydsii.tp2.controller;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.dto.EstadisticasDTO;
import com.aydsii.tp2.dto.VentaDTO;

@RestController
public class VentaController {

    @PostMapping("/api/ventas/estadisticas")
    public ApiResponse<EstadisticasDTO> procesarEstadisticas(@RequestBody @Valid List<VentaDTO> ventaDTO) {
        Double totalFacturado = 0.0;
        Integer cantidadVentas = 0;
        double ticketPromedio = 0;
        VentaDTO ventaMayor = null;
        double mayorMonto = -1.0;
        double montoActual = 0;
        VentaDTO ventaMenor = null;
        double menorMonto = Double.MAX_VALUE;


        ApiResponse<EstadisticasDTO> respuesta = new ApiResponse<>();
        respuesta.setStatus(200);
        respuesta.setMessage("Operación realizada con éxito");

        EstadisticasDTO respuestaDTO = new EstadisticasDTO();
        
        for(VentaDTO venta : ventaDTO){
            totalFacturado += venta.getCantidad() * venta.getPrecioUnitario();

            montoActual = venta.getCantidad() * venta.getPrecioUnitario();
            if(mayorMonto == -1.0 || montoActual > mayorMonto){
                mayorMonto = montoActual;
                ventaMayor = venta;
            }
            if(menorMonto == -1.0 || montoActual < menorMonto){
                menorMonto = montoActual;
                ventaMenor = venta;
            }
        }

        cantidadVentas = ventaDTO.size();

        ticketPromedio = totalFacturado / cantidadVentas;

        respuestaDTO.setTotalFacturado(totalFacturado);
        respuestaDTO.setCantidadVentas(cantidadVentas);
        respuestaDTO.setTicketPromedio(ticketPromedio);
        respuestaDTO.setVentaMayor(ventaMayor);
        respuestaDTO.setVentaMenor(ventaMenor);
        respuesta.setData(respuestaDTO);

        return respuesta;

    }
    
}
