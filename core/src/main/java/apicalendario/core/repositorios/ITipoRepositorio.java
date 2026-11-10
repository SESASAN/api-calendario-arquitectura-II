package apicalendario.core.repositorios;

import java.util.List;
import java.util.Optional;

import apicalendario.dominio.entidades.Tipo;

public interface ITipoRepositorio {

    List<Tipo> listar();

    Optional<Tipo> obtener(long id);

    List<Tipo> buscar(String nombre);

    boolean existeNombre(String nombre, long idExcluido);

    boolean estaEnUso(long id);

    Tipo guardar(Tipo tipo);

    boolean eliminar(long id);
}
