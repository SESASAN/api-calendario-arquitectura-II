package apicalendario.core.excepciones;

public class DatosNoValidosException extends RuntimeException {

    public DatosNoValidosException(String mensaje) {
        super(mensaje);
    }
}
