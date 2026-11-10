package apicalendario.presentacion.dtos;

import java.time.LocalDateTime;

public record ErrorRespuesta(LocalDateTime fecha, int estado, String error, String mensaje) {

    public ErrorRespuesta(int estado, String error, String mensaje) {
        this(LocalDateTime.now(), estado, error, mensaje);
    }
}
