package apicalendario.core.excepciones;

public class AnioNoValidoException extends RuntimeException {

    public AnioNoValidoException(int anio) {
        super("Año no válido: " + anio);
    }
}
