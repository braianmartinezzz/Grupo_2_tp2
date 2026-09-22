package com.aydsii.tp2.exception;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import com.aydsii.tp2.dto.ApiResponse;
import com.aydsii.tp2.dto.ventas.ErrorValidacionDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Se dispara cuando falla la validación de un @Valid @RequestBody (Bean
    // Validation).
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidation(MethodArgumentNotValidException ex) {

        // Detectamos si el error proviene de una lista (Ejercicio 1) buscando corchetes
        // en los campos
        boolean esLista = ex.getBindingResult().getFieldErrors().stream()
                .anyMatch(error -> error.getField().contains("["));

        if (esLista) {
            // Lógica original para el Ejercicio 1
            List<ErrorValidacionDTO> errores = ex.getBindingResult().getFieldErrors().stream()
                    .map(this::mapearError)
                    .toList();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Error de validación en la lista de ventas",
                            errores));
        } else {
            // Lógica para el Ejercicio 4: Agrupar los errores en formato clave-valor
            Map<String, String> errores = new java.util.HashMap<>();
            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errores.put(error.getField(), error.getDefaultMessage());
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Error de validación", errores));
        }
    }

    private ErrorValidacionDTO mapearError(FieldError error) {
        String field = error.getField();
        int posicion = -1; // Si llega a haber un problema de parseo por los tipos de datos, se asigna -1
        if (field.contains("[") && field.contains("]")) {
            try {
                posicion = Integer.parseInt(field.substring(field.indexOf("[") + 1, field.indexOf("]")));
            } catch (NumberFormatException e) {
                posicion = -1;
            }
        }
        String campo = field.contains(".") ? field.substring(field.indexOf(".") + 1) : field;
        return new ErrorValidacionDTO(posicion, campo, error.getDefaultMessage());
    }

    // Se dispara cuando falla la validación de @RequestParam / @PathVariable
    // (@Positive, @Pattern, ...).
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Object>> handleParametroInvalido(HandlerMethodValidationException ex) {
        String mensaje = ex.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(), mensaje));
    }

    // Se dispara cuando ocurre cualquier otra excepción no controlada.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error interno del servidor"));
    }

    // Se dispara cuando falta un parámetro requerido en la solicitud.
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Object>> handleParametroFaltante(MissingServletRequestParameterException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.error(HttpStatus.BAD_REQUEST.value(),
                        "Falta un parámetro requerido: " + ex.getParameterName()));
    }

}
