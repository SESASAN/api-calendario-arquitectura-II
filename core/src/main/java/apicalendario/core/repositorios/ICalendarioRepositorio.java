package apicalendario.core.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import apicalendario.dominio.entidades.Calendario;

public interface ICalendarioRepositorio {

    List<Calendario> listarPorAnio(int anio);

    Optional<Calendario> obtener(LocalDate fecha);

    Calendario guardar(Calendario calendario);

    List<Calendario> reemplazarAnio(int anio, List<Calendario> dias);
}
