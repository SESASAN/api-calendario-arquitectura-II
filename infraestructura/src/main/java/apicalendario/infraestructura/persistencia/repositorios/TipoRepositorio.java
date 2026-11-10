package apicalendario.infraestructura.persistencia.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import apicalendario.core.repositorios.ITipoRepositorio;
import apicalendario.dominio.entidades.Tipo;
import apicalendario.infraestructura.persistencia.mapeadores.TipoMapeador;
import apicalendario.infraestructura.persistencia.repositorios.interfaces.ICalendarioRepositorioJpa;
import apicalendario.infraestructura.persistencia.repositorios.interfaces.ITipoRepositorioJpa;

@Component
public class TipoRepositorio implements ITipoRepositorio {

    private final ITipoRepositorioJpa repositorio;
    private final ICalendarioRepositorioJpa repositorioCalendario;

    public TipoRepositorio(ITipoRepositorioJpa repositorio, ICalendarioRepositorioJpa repositorioCalendario) {
        this.repositorio = repositorio;
        this.repositorioCalendario = repositorioCalendario;
    }

    @Override
    public List<Tipo> listar() {
        return repositorio.findAll(Sort.by("id")).stream().map(TipoMapeador::aDominio).toList();
    }

    @Override
    public Optional<Tipo> obtener(long id) {
        return repositorio.findById(id).map(TipoMapeador::aDominio);
    }

    @Override
    public List<Tipo> buscar(String nombre) {
        return repositorio.findByTipoContainingIgnoreCaseOrderByIdAsc(nombre).stream()
                .map(TipoMapeador::aDominio)
                .toList();
    }

    @Override
    public boolean existeNombre(String nombre, long idExcluido) {
        return repositorio.existsByTipoIgnoreCaseAndIdNot(nombre, idExcluido);
    }

    @Override
    public boolean estaEnUso(long id) {
        return repositorioCalendario.existsByTipoId(id);
    }

    @Override
    public Tipo guardar(Tipo tipo) {
        return TipoMapeador.aDominio(repositorio.save(TipoMapeador.aEntidad(tipo)));
    }

    @Override
    public boolean eliminar(long id) {
        try {
            if (!repositorio.existsById(id)) {
                return false;
            }
            repositorio.deleteById(id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
