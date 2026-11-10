package apicalendario.core.excepciones;

public class TipoNoEncontradoException extends RuntimeException {

    public TipoNoEncontradoException(long id) {
        super("No existe el tipo de día " + id);
    }
}
