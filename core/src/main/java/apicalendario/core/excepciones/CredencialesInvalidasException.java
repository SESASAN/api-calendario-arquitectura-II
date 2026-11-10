package apicalendario.core.excepciones;

public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException() {
        super("Usuario o clave incorrectos");
    }
}
