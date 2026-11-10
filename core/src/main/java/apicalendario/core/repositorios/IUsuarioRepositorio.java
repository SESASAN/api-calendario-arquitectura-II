package apicalendario.core.repositorios;

import java.util.Optional;

import apicalendario.dominio.entidades.Usuario;

public interface IUsuarioRepositorio {

    Optional<Usuario> obtener(String nombreUsuario);

    boolean existe(String nombreUsuario);

    Usuario guardar(Usuario usuario);
}
