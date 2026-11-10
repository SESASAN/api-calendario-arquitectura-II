package apicalendario.presentacion.manejadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import apicalendario.core.excepciones.AnioNoValidoException;
import apicalendario.core.excepciones.ConflictoException;
import apicalendario.core.excepciones.CredencialesInvalidasException;
import apicalendario.core.excepciones.DatosNoValidosException;
import apicalendario.core.excepciones.ServicioFestivosException;
import apicalendario.core.excepciones.TipoNoEncontradoException;
import apicalendario.presentacion.dtos.ErrorRespuesta;

@RestControllerAdvice
public class ExcepcionesGlobalesHandler {

    private static final Logger LOG = LoggerFactory.getLogger(ExcepcionesGlobalesHandler.class);

    @ExceptionHandler({ AnioNoValidoException.class, DatosNoValidosException.class })
    public ResponseEntity<ErrorRespuesta> manejarDatosNoValidos(RuntimeException e) {
        return responder(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class })
    public ResponseEntity<ErrorRespuesta> manejarSolicitudMalFormada(Exception e) {
        LOG.warn("Solicitud mal formada: {}", e.getMessage());
        return responder(HttpStatus.BAD_REQUEST, "Solicitud mal formada o parámetro no válido");
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<ErrorRespuesta> manejarCredenciales(CredencialesInvalidasException e) {
        return responder(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(TipoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> manejarNoEncontrado(TipoNoEncontradoException e) {
        return responder(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<ErrorRespuesta> manejarConflicto(ConflictoException e) {
        return responder(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(ServicioFestivosException.class)
    public ResponseEntity<ErrorRespuesta> manejarFestivos(ServicioFestivosException e) {
        LOG.error(e.getMessage(), e);
        return responder(HttpStatus.BAD_GATEWAY, e.getMessage());
    }

    private static ResponseEntity<ErrorRespuesta> responder(HttpStatus estado, String mensaje) {
        return ResponseEntity.status(estado)
                .body(new ErrorRespuesta(estado.value(), estado.getReasonPhrase(), mensaje));
    }
}
