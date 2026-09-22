package com.aydsii.tp2.service;

import com.aydsii.tp2.dto.ClienteDTO;
import com.aydsii.tp2.model.Cliente;
import com.aydsii.tp2.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(ClienteDTO dto) {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre(dto.getNombre());
        nuevoCliente.setApellido(dto.getApellido());
        nuevoCliente.setEmail(dto.getEmail());
        nuevoCliente.setTelefono(dto.getTelefono());
        nuevoCliente.setFechaRegistro(LocalDateTime.now());
        
        return clienteRepository.save(nuevoCliente);
    }

    public boolean existeEmail(String email) {
        return clienteRepository.existsByEmail(email);
    }
}