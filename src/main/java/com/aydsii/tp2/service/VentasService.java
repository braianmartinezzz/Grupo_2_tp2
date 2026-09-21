package com.aydsii.tp2.service;

import java.util.*;

import org.springframework.stereotype.Service;

import com.aydsii.tp2.dto.ventas.DescuentoResponseDTO;
import com.aydsii.tp2.dto.ventas.EstadisticaResponseDTO;
import com.aydsii.tp2.dto.ventas.VentaConDescuentoDTO;
import com.aydsii.tp2.dto.ventas.VentaConImporteDTO;
import com.aydsii.tp2.dto.ventas.VentaDTO;

@Service 
public class VentasService {

    public EstadisticaResponseDTO getEstadisticas(List<VentaDTO> ventasDTO) {
        double totalFacturado = 0;
        VentaDTO ventaMayor = null;
        VentaDTO ventaMenor = null;
        double importeMayor = Double.NEGATIVE_INFINITY;
        double importeMenor = Double.POSITIVE_INFINITY;
        Map<String, Integer> cantidadPorProducto = new LinkedHashMap<>();
        
        for(VentaDTO venta : ventasDTO){
            double importe = venta.getCantidad() * venta.getPrecioUnitario();
            totalFacturado += importe;

            if (importe > importeMayor) {
                importeMayor = importe;
                ventaMayor = venta;
            }
            if (importe < importeMenor) {
                importeMenor = importe;
                ventaMenor = venta;
            }

            cantidadPorProducto.merge(venta.getProducto(), venta.getCantidad(), Integer::sum);
        }

        int cantidadVentas = ventasDTO.size();
        double ticketPromedio = totalFacturado / cantidadVentas;
        String productoMasVendido = cantidadPorProducto.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow();
        
        return new EstadisticaResponseDTO(
                totalFacturado, 
                cantidadVentas, 
                ticketPromedio, 
                conImporte(ventaMayor, importeMayor), 
                conImporte(ventaMenor, importeMenor), 
                productoMasVendido);
    }

    private VentaConImporteDTO conImporte(VentaDTO venta, double importe) {
        return new VentaConImporteDTO(venta.getProducto(), venta.getCantidad(), venta.getPrecioUnitario(), importe);
    }

    public DescuentoResponseDTO aplicarDescuento(List<VentaDTO> ventasDTO, double porcentajeDescuento) {
        List<VentaConDescuentoDTO> ventasConDescuento = new ArrayList<>();
        double totalConDescuento = 0;

        for (VentaDTO venta : ventasDTO) {
            double importe = venta.getCantidad() * venta.getPrecioUnitario();
            double montoConDescuento = importe - (importe * porcentajeDescuento / 100);
            totalConDescuento += montoConDescuento;
            ventasConDescuento.add(new VentaConDescuentoDTO(venta.getProducto(), venta.getCantidad(), venta.getPrecioUnitario(), montoConDescuento));
        }

        return new DescuentoResponseDTO(ventasConDescuento, totalConDescuento);
    }
}
