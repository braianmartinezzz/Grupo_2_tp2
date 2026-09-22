package com.aydsii.tp2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteDTO {

    @NotBlank(message = "El nombre no puede estar vacio")
    @Size(min = 2, message = "El nombre debe tener al menos 2 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacio")
    @Size(min = 2, message = "El apellido debe tener al menos 2 caracteres")
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email valido")
    private String email;

    @Pattern(regexp = "^\\d*$", message = "Solo debe contener digitos")
    private String telefono;
}