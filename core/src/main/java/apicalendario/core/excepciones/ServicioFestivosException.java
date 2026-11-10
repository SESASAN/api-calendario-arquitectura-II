package apicalendario.core.excepciones;

public class ServicioFestivosException extends RuntimeException {

    public ServicioFestivosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
