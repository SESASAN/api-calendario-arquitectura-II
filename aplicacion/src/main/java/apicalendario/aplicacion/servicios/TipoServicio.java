package apicalendario.aplicacion.servicios;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import apicalendario.core.excepciones.ConflictoException;
import apicalendario.core.excepciones.DatosNoValidosException;
import apicalendario.core.excepciones.TipoNoEncontradoException;
import apicalendario.core.repositorios.ITipoRepositorio;
import apicalendario.core.servicios.ITipoServicio;
import apicalendario.dominio.entidades.Tipo;

@Service
public class TipoServicio implements ITipoServicio {

    private static final Set<Long> TIPOS_BASE = Set.of(Tipo.DIA_LABORAL, Tipo.FIN_DE_SEMANA, Tipo.DIA_FESTIVO);

    private final ITipoRepositorio repositorio;

    public TipoServicio(ITipoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public List<Tipo> listar() {
        return repositorio.listar();
    }

    @Override
    public Tipo obtener(long id) {
        return repositorio.obtener(id).orElseThrow(() -> new TipoNoEncontradoException(id));
    }

    @Override
    public List<Tipo> buscar(String nombre) {
        return repositorio.buscar(nombre.trim());
    }

    @Override
    public Tipo agregar(Tipo tipo) {
        String nombre = validarNombre(tipo);
        if (repositorio.existeNombre(nombre, 0)) {
            throw new ConflictoException("Ya existe un tipo llamado '" + nombre + "'");
        }
        return repositorio.guardar(new Tipo(0, nombre));
    }

    @Override
    public Tipo modificar(Tipo tipo) {
        obtener(tipo.getId());
        String nombre = validarNombre(tipo);
        if (repositorio.existeNombre(nombre, tipo.getId())) {
            throw new ConflictoException("Ya existe un tipo llamado '" + nombre + "'");
        }
        return repositorio.guardar(new Tipo(tipo.getId(), nombre));
    }

    @Override
    public boolean eliminar(long id) {
        obtener(id);
        if (TIPOS_BASE.contains(id)) {
            throw new ConflictoException("El tipo " + id + " es necesario para generar el calendario");
        }
        if (repositorio.estaEnUso(id)) {
            throw new ConflictoException("El tipo " + id + " está asignado a días del calendario");
        }
        return repositorio.eliminar(id);
    }

    private static String validarNombre(Tipo tipo) {
        if (tipo == null || tipo.getTipo() == null || tipo.getTipo().isBlank()) {
            throw new DatosNoValidosException("El nombre del tipo es obligatorio");
        }
        String nombre = tipo.getTipo().trim();
        if (nombre.length() > 100) {
            throw new DatosNoValidosException("El nombre del tipo admite máximo 100 caracteres");
        }
        return nombre;
    }
}
