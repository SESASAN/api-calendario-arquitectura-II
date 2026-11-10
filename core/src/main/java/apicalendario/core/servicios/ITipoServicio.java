package apicalendario.core.servicios;

import java.util.List;

import apicalendario.dominio.entidades.Tipo;

public interface ITipoServicio {

    List<Tipo> listar();

    Tipo obtener(long id);

    List<Tipo> buscar(String nombre);

    Tipo agregar(Tipo tipo);

    Tipo modificar(Tipo tipo);

    boolean eliminar(long id);
}
