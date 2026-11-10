package apicalendario.infraestructura.persistencia.repositorios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import apicalendario.core.repositorios.ICalendarioRepositorio;
import apicalendario.dominio.entidades.Calendario;
import apicalendario.infraestructura.persistencia.mapeadores.CalendarioMapeador;
import apicalendario.infraestructura.persistencia.repositorios.interfaces.ICalendarioRepositorioJpa;

@Component
public class CalendarioRepositorio implements ICalendarioRepositorio {

    private final ICalendarioRepositorioJpa repositorio;

    public CalendarioRepositorio(ICalendarioRepositorioJpa repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Calendario> listarPorAnio(int anio) {
        return repositorio.findByFechaBetweenOrderByFechaAsc(LocalDate.of(anio, 1, 1), LocalDate.of(anio, 12, 31))
                .stream()
                .map(CalendarioMapeador::aDominio)
                .toList();
    }

    @Override
    public Optional<Calendario> obtener(LocalDate fecha) {
        return repositorio.findByFecha(fecha).map(CalendarioMapeador::aDominio);
    }

    @Override
    public Calendario guardar(Calendario calendario) {
        return CalendarioMapeador.aDominio(repositorio.save(CalendarioMapeador.aEntidad(calendario)));
    }

    @Override
    @Transactional
    public List<Calendario> reemplazarAnio(int anio, List<Calendario> dias) {
        repositorio.eliminarEntre(LocalDate.of(anio, 1, 1), LocalDate.of(anio, 12, 31));
        repositorio.flush();
        return repositorio.saveAll(dias.stream().map(CalendarioMapeador::aEntidad).toList())
                .stream()
                .map(CalendarioMapeador::aDominio)
                .toList();
    }
}
