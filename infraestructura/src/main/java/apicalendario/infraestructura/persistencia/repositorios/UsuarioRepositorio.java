package apicalendario.infraestructura.persistencia.repositorios;

import java.util.Optional;

import org.springframework.stereotype.Component;

import apicalendario.core.repositorios.IUsuarioRepositorio;
import apicalendario.dominio.entidades.Usuario;
import apicalendario.infraestructura.persistencia.mapeadores.UsuarioMapeador;
import apicalendario.infraestructura.persistencia.repositorios.interfaces.IUsuarioRepositorioJpa;

@Component
public class UsuarioRepositorio implements IUsuarioRepositorio {

    private final IUsuarioRepositorioJpa repositorio;

    public UsuarioRepositorio(IUsuarioRepositorioJpa repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Optional<Usuario> obtener(String nombreUsuario) {
        return repositorio.findByUsuario(nombreUsuario).map(UsuarioMapeador::aDominio);
    }

    @Override
    public boolean existe(String nombreUsuario) {
        return repositorio.existsByUsuario(nombreUsuario);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        return UsuarioMapeador.aDominio(repositorio.save(UsuarioMapeador.aEntidad(usuario)));
    }
}
